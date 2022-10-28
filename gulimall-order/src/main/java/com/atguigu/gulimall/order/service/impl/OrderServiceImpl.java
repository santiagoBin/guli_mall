package com.atguigu.gulimall.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.gulimall.common.exception.NoStockException;
import com.atguigu.gulimall.common.to.cart.CartItemInOrderTo;
import com.atguigu.gulimall.common.to.cart.SkuInfoTo;
import com.atguigu.gulimall.common.to.member.MemberAddrTo;
import com.atguigu.gulimall.common.to.member.MemberTo;
import com.atguigu.gulimall.common.to.mq.SeckillOrderTo;
import com.atguigu.gulimall.common.to.order.OrderTo;
import com.atguigu.gulimall.common.to.product.SpuInfoTo;
import com.atguigu.gulimall.common.utils.HttpClient;
import com.atguigu.gulimall.common.utils.R;
import com.atguigu.gulimall.common.vo.MemberRespVo;
import com.atguigu.gulimall.order.constant.PayConstant;
import com.atguigu.gulimall.order.entity.OrderItemEntity;
import com.atguigu.gulimall.order.entity.PaymentInfoEntity;
import com.atguigu.gulimall.order.enume.OrderStatusEnum;
import com.atguigu.gulimall.order.feign.CartFeignClient;
import com.atguigu.gulimall.order.feign.MemberFeignClient;
import com.atguigu.gulimall.order.feign.ProductFeignClient;
import com.atguigu.gulimall.order.feign.WareFeignClient;
import com.atguigu.gulimall.order.interceptor.LoginUserInterceptor;
import com.atguigu.gulimall.order.service.OrderItemService;
import com.atguigu.gulimall.order.service.PaymentInfoService;
import com.atguigu.gulimall.order.to.OrderCreateTo;
import com.atguigu.gulimall.order.vo.*;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.wxpay.sdk.WXPayUtil;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.common.utils.Query;

import com.atguigu.gulimall.order.dao.OrderDao;
import com.atguigu.gulimall.order.entity.OrderEntity;
import com.atguigu.gulimall.order.service.OrderService;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import static com.atguigu.gulimall.common.constant.CartConstant.CART_PREFIX;
import static com.atguigu.gulimall.order.constant.OrderConstant.USER_ORDER_TOKEN_PREFIX;
@Slf4j
@EnableTransactionManagement
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    public static ThreadLocal<OrderSubmitVo> orderSubmitVoThreadLocal = new ThreadLocal<>();

    @Autowired
    MemberFeignClient memberFeignClient;
    @Autowired
    ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    CartFeignClient cartFeignClient;
    @Autowired
    WareFeignClient wareFeignClient;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    ProductFeignClient productFeignClient;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    PaymentInfoService paymentInfoService;
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    BestPayService bestPayService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException {
        OrderConfirmVo orderConfirmVo = new OrderConfirmVo();
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        CompletableFuture<Void> memberAddressFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<MemberAddrTo> memberAddressList = memberFeignClient.getMemberAddress(Long.parseLong(memberRespVo.getId()));
            orderConfirmVo.setMemberAddressVos(memberAddressList);
        }, threadPoolExecutor);

        CompletableFuture<Void> itemFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<CartItemInOrderTo> userCurrentCartItems = cartFeignClient.getUserCurrentCartItems();
            orderConfirmVo.setItems(userCurrentCartItems);
        }, threadPoolExecutor).thenRunAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<CartItemInOrderTo> items = orderConfirmVo.getItems();
            if (items != null && items.size() > 0) {
                List<Long> skuIds = items.stream().map(item -> {
                    Long skuId = item.getSkuId();
                    return skuId;
                }).collect(Collectors.toList());
                R r = wareFeignClient.getSkuHasStock(skuIds);
                List<SkuStockVo> skuStockVoList = r.getData(new TypeReference<List<SkuStockVo>>() {
                });
                if (skuStockVoList != null && skuStockVoList.size() > 0) {
                    Map<Long, Boolean> collect = skuStockVoList.stream().collect(Collectors.toMap(SkuStockVo::getSkuId, SkuStockVo::getHasStock));
                    orderConfirmVo.setStocks(collect);
                }
            }
        }, threadPoolExecutor);
        Integer integration = memberRespVo.getIntegration();
        orderConfirmVo.setIntegration(integration);

        String token = UUID.randomUUID().toString().replace("-","");
        redisTemplate.opsForValue().set(USER_ORDER_TOKEN_PREFIX+memberRespVo.getId(),token,30, TimeUnit.MINUTES);
        orderConfirmVo.setOrderToken(token);

        CompletableFuture.allOf(memberAddressFuture,itemFuture).get();

        return orderConfirmVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SubmitOrderResponseVo submitOrder(OrderSubmitVo submitVo) {
        orderSubmitVoThreadLocal.set(submitVo);
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        SubmitOrderResponseVo submitOrderResponseVo = new SubmitOrderResponseVo();
        submitOrderResponseVo.setCode(0);
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        String orderToken = submitVo.getOrderToken();
        Long result = redisTemplate.execute(new DefaultRedisScript<Long>(script,Long.class), Arrays.asList(USER_ORDER_TOKEN_PREFIX+memberRespVo.getId()),orderToken);
        if (result == 0L){
            submitOrderResponseVo.setCode(1);
            return submitOrderResponseVo;
        }else {
            OrderCreateTo order = createOrder();
            BigDecimal payAmount = order.getOrder().getPayAmount();
            BigDecimal payPrice = submitVo.getPayPrice();
            if (Math.abs(payAmount.subtract(payPrice).doubleValue())<0.01){
                saveOrder(order);
                WareSkuLockVo wareSkuLockVo = new WareSkuLockVo();
                wareSkuLockVo.setOrderSn(order.getOrder().getOrderSn());
                List<CartItemInOrderTo> collect = order.getOrderItems().stream().map(item -> {
                    CartItemInOrderTo cartItemInOrderTo = new CartItemInOrderTo();
                    cartItemInOrderTo.setSkuId(item.getSkuId());
                    cartItemInOrderTo.setCount(item.getSkuQuantity());
                    cartItemInOrderTo.setTitle(item.getSkuName());
                    return cartItemInOrderTo;
                }).collect(Collectors.toList());
                wareSkuLockVo.setLocks(collect);
                R r = wareFeignClient.orderLockStock(wareSkuLockVo);
                if (r.getCode() == 0){
                    submitOrderResponseVo.setOrder(order.getOrder());
                    rabbitTemplate.convertAndSend("order-event-exchange","order.create.order",order.getOrder());
                     redisTemplate.delete(CART_PREFIX + memberRespVo.getId());
                     return submitOrderResponseVo;
                }else {
                    String msg = (String) r.get("msg");
                    throw new NoStockException(msg);
                }
            }else {
                submitOrderResponseVo.setCode(2);
                return submitOrderResponseVo;
            }
        }
    }

    @Override
    public R getOrderStatus(String orderSn) {
        OrderEntity orderEntity = this.getOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));
        OrderTo orderTo = new OrderTo();
        System.out.println(orderEntity);
        BeanUtils.copyProperties(orderEntity,orderTo);
        return R.ok().setData(orderTo);
    }

    @Override
    public void closeOrder(OrderEntity orderEntity) {
        OrderEntity order = this.getOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderEntity.getOrderSn()));
        if (order.getStatus().equals(OrderStatusEnum.CREATE_NEW.getCode())){
            OrderEntity orderUpdate = new OrderEntity();
            orderUpdate.setId(order.getId());
            orderUpdate.setStatus(OrderStatusEnum.CANCLED.getCode());
            this.updateById(orderUpdate);
            OrderTo orderTo = new OrderTo();
            BeanUtils.copyProperties(order,orderTo);
            try{
                rabbitTemplate.convertAndSend("order-event-exchange","order.release.other",orderTo);
            }catch (Exception e){

            }

        }

    }

    @Override
    public void closeSeckillOrder(SeckillOrderTo orderTo) {
        OrderEntity order = this.getOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderTo.getOrderSn()));
        if (order.getStatus().equals(OrderStatusEnum.CREATE_NEW.getCode())) {
            OrderEntity orderUpdate = new OrderEntity();
            orderUpdate.setId(order.getId());
            orderUpdate.setStatus(OrderStatusEnum.CANCLED.getCode());
            this.updateById(orderUpdate);
            String redisStockKey = "seckill:stock:" + orderTo.getRandomCode();
            RSemaphore semaphore = redissonClient.getSemaphore(redisStockKey);
            semaphore.release(orderTo.getNum());
            String secKillUserKey = orderTo.getMemberId().toString() + orderTo.getPromotionSessionId().toString() + orderTo.getSkuId().toString();
            redisTemplate.delete(secKillUserKey);
        }
    }

    @Override
    public PayVo getOrderPay(String orderSn) {
        PayVo payVo = new PayVo();
        OrderEntity orderEntity = this.getOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));
        BigDecimal bigDecimal = orderEntity.getPayAmount().setScale(2, BigDecimal.ROUND_UP);
        payVo.setTotal_amount(bigDecimal.toString());
        payVo.setOut_trade_no(orderEntity.getOrderSn());
        List<OrderItemEntity> l = orderItemService.list(new QueryWrapper<OrderItemEntity>().eq("order_sn", orderSn));
        OrderItemEntity orderItemEntity = l.get(0);
        payVo.setBody(orderItemEntity.getSkuAttrsVals());
        payVo.setSubject(orderItemEntity.getSkuName());
        return payVo;
    }

    @Override
    public PageUtils queryPageWithItem(Map<String, Object> params) {
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        IPage<OrderEntity> page = this.page(new Query<OrderEntity>().getPage(params), new QueryWrapper<OrderEntity>().eq("member_id", memberRespVo.getId()).orderByDesc("create_time"));
        List<OrderEntity> orderList = page.getRecords().stream().map(order -> {
            List<OrderItemEntity> orderItemEntityList = orderItemService.list(new QueryWrapper<OrderItemEntity>().eq("order_sn", order.getOrderSn()));
            order.setOrderItemEntityList(orderItemEntityList);
            return order;
        }).collect(Collectors.toList());
        page.setRecords(orderList);
        return new PageUtils(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String handlePayResult(PayAsyncVo asyncVo) {
        PaymentInfoEntity paymentInfoEntity = new PaymentInfoEntity();
        paymentInfoEntity.setOrderSn(asyncVo.getOut_trade_no());
        paymentInfoEntity.setAlipayTradeNo(asyncVo.getTrade_no());
        paymentInfoEntity.setTotalAmount(new BigDecimal(asyncVo.getTotal_amount()));
        paymentInfoEntity.setSubject(asyncVo.getBody());
        paymentInfoEntity.setPaymentStatus(asyncVo.getTrade_status());
        paymentInfoEntity.setCallbackTime(asyncVo.getNotify_time());
        paymentInfoService.save(paymentInfoEntity);
        String trade_status = asyncVo.getTrade_status();
        if (trade_status.equals("TRADE_SUCCESS") || trade_status.equals("TRADE_FINISHED")){
            String orderSn = asyncVo.getOut_trade_no();
            this.updateOrderStatus(orderSn,OrderStatusEnum.PAYED.getCode(), PayConstant.ALIPAY);
        }
        return "success";
    }



    @Override
    @Transactional
    public void createSeckillOrder(SeckillOrderTo orderTo) {
        try{
            //TODO 保存订单信息
            List<MemberAddrTo> memberAddress = memberFeignClient.getMemberAddress(orderTo.getMemberId());
            MemberTo member = memberFeignClient.getMemberById(orderTo.getMemberId().toString());
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setMemberUsername(member.getUsername());
            orderEntity.setOrderSn(orderTo.getOrderSn());
            orderEntity.setMemberId(orderTo.getMemberId());
            orderEntity.setCreateTime(new Date());
            BigDecimal totalPrice = orderTo.getSeckillPrice().multiply(BigDecimal.valueOf(orderTo.getNum()));
            orderEntity.setPayAmount(totalPrice);
            orderEntity.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
            MemberAddrTo memberAddrTo = memberAddress.get(0);
            orderEntity.setReceiverCity(memberAddrTo.getCity());
            orderEntity.setReceiverRegion(memberAddrTo.getRegion());
            orderEntity.setReceiverDetailAddress(memberAddrTo.getDetailAddress());
            orderEntity.setReceiverProvince(memberAddrTo.getProvince());
            orderEntity.setReceiverName(memberAddrTo.getName());
            orderEntity.setReceiverPostCode(memberAddrTo.getPostCode());
            orderEntity.setReceiverPhone(memberAddrTo.getPhone());
            System.out.println(1);
            //保存订单
            this.save(orderEntity);

            //保存订单项信息
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrderSn(orderTo.getOrderSn());
            orderItem.setRealAmount(totalPrice);

            orderItem.setSkuQuantity(orderTo.getNum());

            //保存商品的spu信息
            System.out.println(2);
            R spuInfo = productFeignClient.getSpuInfoBySkuId(orderTo.getSkuId());
            System.out.println(3);
            SpuInfoTo spuInfoData = spuInfo.getData( new TypeReference<SpuInfoTo>() {
            });
            System.out.println(4);
            orderItem.setSpuId(spuInfoData.getId());
            orderItem.setSpuName(spuInfoData.getSpuName());
            orderItem.setSpuBrand(spuInfoData.getBrandName());
            orderItem.setCategoryId(spuInfoData.getCatelogId());
            R skuInfo = productFeignClient.info(orderTo.getSkuId());
            String sku = JSON.toJSONString(skuInfo.get("skuInfo"));
            SkuInfoTo skuInfoTo = JSON.parseObject(sku, SkuInfoTo.class);
            orderItem.setSkuId(orderTo.getSkuId());
            orderItem.setSkuPic(skuInfoTo.getSkuDefaultImg());
            orderItem.setSkuName(skuInfoTo.getSkuName());

            //保存订单项数据
            orderItemService.save(orderItem);
            rabbitTemplate.convertAndSend("order-event-exchange","order.create.order",orderTo);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void updateOrderStatus(String orderSn, Integer code, Integer payType) {
        this.baseMapper.updateOrderStatus(orderSn,code,payType);
    }


    private void saveOrder(OrderCreateTo order) {
        OrderEntity orderEntity = order.getOrder();
        this.save(orderEntity);
        List<OrderItemEntity> orderItems = order.getOrderItems();
        orderItemService.saveBatch(orderItems);
    }

    private OrderCreateTo createOrder() {
        OrderCreateTo orderCreateTo = new OrderCreateTo();
        String orderSn = IdWorker.getTimeId();
        OrderEntity orderEntity = builderOrder(orderSn);
        List<OrderItemEntity> orderItemEntities = builderOrderItems(orderSn);
        computePrice(orderEntity,orderItemEntities);
        orderCreateTo.setOrder(orderEntity);
        orderCreateTo.setOrderItems(orderItemEntities);
        return orderCreateTo;
    }

    private void computePrice(OrderEntity orderEntity, List<OrderItemEntity> orderItemEntities) {
        BigDecimal total = new BigDecimal("0.0");
        BigDecimal coupon = new BigDecimal("0.0");
        BigDecimal integration = new BigDecimal("0.0");
        BigDecimal promotion = new BigDecimal("0.0");
        Integer integrationTotal = 0;
        Integer growthTotal = 0;
        for (OrderItemEntity orderItem: orderItemEntities
             ) {
            coupon = coupon.add(orderItem.getCouponAmount());
            promotion = promotion.add(orderItem.getPromotionAmount());
            integration = integration.add(orderItem.getIntegrationAmount());

            total = total.add(orderItem.getRealAmount());

            integrationTotal += orderItem.getGiftIntegration();
            growthTotal += orderItem.getGiftGrowth();
        }
        orderEntity.setTotalAmount(total);
        orderEntity.setPayAmount(total.add(orderEntity.getFreightAmount()));
        orderEntity.setCouponAmount(coupon);
        orderEntity.setPromotionAmount(promotion);
        orderEntity.setIntegrationAmount(promotion);
        orderEntity.setIntegration(integrationTotal);
        orderEntity.setGrowth(growthTotal);
        orderEntity.setDeleteStatus(0);
    }

    private List<OrderItemEntity> builderOrderItems(String orderSn) {
        List<OrderItemEntity> orderItemEntityList = new ArrayList<>();
        List<CartItemInOrderTo> userCurrentCartItems = cartFeignClient.getUserCurrentCartItems();
        if (userCurrentCartItems!=null&&userCurrentCartItems.size()>0){
            orderItemEntityList = userCurrentCartItems.stream().map(item -> {
                OrderItemEntity orderItemEntity = builderOrderItem(item);
                orderItemEntity.setOrderSn(orderSn);
                return orderItemEntity;
            }).collect(Collectors.toList());
        }
        return orderItemEntityList;
    }

    private OrderItemEntity builderOrderItem(CartItemInOrderTo item) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        Long skuId = item.getSkuId();
        R r = productFeignClient.getSpuInfoBySkuId(skuId);
        SpuInfoTo spuInfoData = r.getData(new TypeReference<SpuInfoTo>() {
        });
        orderItemEntity.setSpuId(spuInfoData.getId());
        orderItemEntity.setSpuName(spuInfoData.getSpuName());
        orderItemEntity.setSpuBrand(spuInfoData.getBrandName());
        orderItemEntity.setCategoryId(spuInfoData.getCatelogId());
        orderItemEntity.setSkuId(skuId);
        orderItemEntity.setSkuName(item.getTitle());
        orderItemEntity.setSkuPic(item.getImage());
        orderItemEntity.setSkuPrice(item.getPrice());
        orderItemEntity.setSkuQuantity(item.getCount());
        String skuAttrValues = StringUtils.collectionToDelimitedString(item.getSkuAttrValues(), ",");
        orderItemEntity.setSkuAttrsVals(skuAttrValues);
        orderItemEntity.setGiftGrowth(item.getPrice().multiply(new BigDecimal(item.getCount())).intValue());
        orderItemEntity.setGiftIntegration(item.getPrice().multiply(new BigDecimal(item.getCount())).intValue());
        orderItemEntity.setPromotionAmount(BigDecimal.ZERO);
        orderItemEntity.setCouponAmount(BigDecimal.ZERO);
        orderItemEntity.setIntegrationAmount(BigDecimal.ZERO);
        BigDecimal origin = orderItemEntity.getSkuPrice().multiply(new BigDecimal(orderItemEntity.getSkuQuantity().toString()));
        BigDecimal real = origin.subtract(orderItemEntity.getCouponAmount()).subtract(orderItemEntity.getIntegrationAmount()).subtract(orderItemEntity.getPromotionAmount());
        orderItemEntity.setRealAmount(real);
        return orderItemEntity;


    }

    private OrderEntity builderOrder(String orderSn) {
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setMemberId(Long.parseLong(memberRespVo.getId()));
        orderEntity.setOrderSn(orderSn);
        orderEntity.setOrderSn32BitForWxPay(orderSn.substring(0,32));
        orderEntity.setMemberUsername(memberRespVo.getUsername());
        OrderSubmitVo orderSubmitVo = orderSubmitVoThreadLocal.get();
        R fareAddrVo = wareFeignClient.getFare(orderSubmitVo.getAddrId());
        FareVo fareResp = fareAddrVo.getData(new TypeReference<FareVo>() {
        });
        BigDecimal fare = fareResp.getFare();
        orderEntity.setFreightAmount(fare);
        MemberAddrTo address = fareResp.getAddress();
        orderEntity.setReceiverName(address.getName());
        orderEntity.setReceiverPhone(address.getPhone());
        orderEntity.setReceiverPostCode(address.getPostCode());
        orderEntity.setReceiverProvince(address.getProvince());
        orderEntity.setReceiverCity(address.getCity());
        orderEntity.setReceiverRegion(address.getRegion());
        orderEntity.setReceiverDetailAddress(address.getDetailAddress());
        orderEntity.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
        orderEntity.setAutoConfirmDay(7);
        orderEntity.setConfirmStatus(0);
        return orderEntity;

    }


    /**
     * 微信异步通知结果
     * @param notifyData
     * @return
     */
    @Override
    public String asyncNotify(String notifyData) {
        System.out.println(notifyData);

        //签名效验
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("payResponse={}",payResponse);

        //2.金额效验（从数据库查订单）
        OrderEntity orderEntity = this.getOrderByOrderSn(payResponse.getOrderId());

        //如果查询出来的数据是null的话
        //比较严重(正常情况下是不会发生的)发出告警：钉钉、短信
        if (orderEntity == null) {
            //TODO 发出告警，钉钉，短信
            throw new RuntimeException("通过订单编号查询出来的结果是null");
        }

        //判断订单状态状态是否为已支付或者是已取消,如果不是订单状态不是已支付状态
        Integer status = orderEntity.getStatus();
        if (status.equals(OrderStatusEnum.PAYED.getCode()) || status.equals(OrderStatusEnum.CANCLED.getCode())) {
            throw new RuntimeException("该订单已失效,orderNo=" + payResponse.getOrderId());
        }

        /*//判断金额是否一致,Double类型比较大小，精度问题不好控制
        if (orderEntity.getPayAmount().compareTo(BigDecimal.valueOf(payResponse.getOrderAmount())) != 0) {
            //TODO 告警
            throw new RuntimeException("异步通知中的金额和数据库里的不一致,orderNo=" + payResponse.getOrderId());
        }*/

        //3.修改订单支付状态
        //支付成功状态
        String orderSn = orderEntity.getOrderSn();
        this.updateOrderStatus(orderSn,OrderStatusEnum.PAYED.getCode(),PayConstant.WXPAY);

        //4.告诉微信不要再重复通知了
        return "<xml>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }


    @Override
    public OrderEntity getOrderBy32BitOrderSn(String orderSnForWxPay) {

        OrderEntity orderEntity = this.baseMapper.selectOne(new QueryWrapper<OrderEntity>().eq("order_sn_for_wx_pay", orderSnForWxPay));

        return orderEntity;
    }

    @Override
    public OrderEntity getOrderByOrderSn(String orderSn) {

        OrderEntity orderEntity = this.baseMapper.selectOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));

        return orderEntity;
    }

    @Override
    public Map<String, String> createNative(String orderSn) {
        try {
            //根据订单id获取订单信息
            QueryWrapper<OrderEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("order_sn_for_wx_pay",orderSn);
            OrderEntity order = this.getOne(wrapper);
            Map m = new HashMap();
//1、设置支付参数
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", "谷粒商城微信支付");
            m.put("out_trade_no", orderSn);
            m.put("total_fee", order.getTotalAmount().multiply(new BigDecimal(100)).longValue()+"");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url",
                    "https://bwdrnq.natappfree.cc/wxPay/notify");//微信二维码支付异步回调地址必须是https且要能公网访问的！
            m.put("trade_type", "NATIVE");
//2、HTTPClient来根据URL访问第三方接口并且传递参数
            HttpClient client = new
                    HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
//client设置参数
            client.setXmlParam(WXPayUtil.generateSignedXml(m,
                    "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
//3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
//4、封装返回结果集
            Map map = new HashMap<>();
            map.put("orderId", order.getOrderSn());
            map.put("codeUrl", resultMap.get("code_url"));
            map.put("returnUrl","http://member.gulimall.com/memberOrder.html");
//微信支付二维码2小时过期，可采取2小时未支付取消订单
//redisTemplate.opsForValue().set(orderNo, map, 120,TimeUnit.MINUTES);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}