package com.atguigu.gulimall.ware.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.atguigu.gulimall.common.exception.NoStockException;
import com.atguigu.gulimall.common.to.WareHasStockTo;
import com.atguigu.gulimall.common.to.mq.StockDetailTo;
import com.atguigu.gulimall.common.to.mq.StockLockedTo;
import com.atguigu.gulimall.common.to.order.OrderTo;
import com.atguigu.gulimall.common.utils.R;
import com.atguigu.gulimall.ware.entity.WareOrderTaskDetailEntity;
import com.atguigu.gulimall.ware.entity.WareOrderTaskEntity;
import com.atguigu.gulimall.ware.feign.OrderFeignClient;
import com.atguigu.gulimall.ware.service.WareOrderTaskDetailService;
import com.atguigu.gulimall.ware.service.WareOrderTaskService;
import com.atguigu.gulimall.ware.vo.OrderItemVo;
import com.atguigu.gulimall.ware.vo.WareSkuLockVo;
import lombok.Data;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.common.utils.Query;

import com.atguigu.gulimall.ware.dao.WareSkuDao;
import com.atguigu.gulimall.ware.entity.WareSkuEntity;
import com.atguigu.gulimall.ware.service.WareSkuService;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@RabbitListener(queues = "stock.release.stock.queue")
@Service("wareSkuService")
@EnableTransactionManagement
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    WareOrderTaskService wareOrderTaskService;
    @Autowired
    WareOrderTaskDetailService wareOrderTaskDetailService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    OrderFeignClient orderFeignClient;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                new QueryWrapper<WareSkuEntity>().eq(!StringUtils.isEmpty(params.get("wareId")),"ware_id",params.get("wareId"))
                .eq(!StringUtils.isEmpty(params.get("skuId")),"sku_id",params.get("skuId"))
        );

        return new PageUtils(page);
    }

    @Override
    public List<WareHasStockTo> hasStock(List<Long> skuIds) {
        List<WareHasStockTo> collect = skuIds.stream().map(skuId -> {
            WareHasStockTo wareHasStockVo = new WareHasStockTo();
            Long count = baseMapper.getSkuStock(skuId);
            if (count!=null){
                wareHasStockVo.setHasStock(count>0);
                wareHasStockVo.setSkuId(skuId);
            }else {
                wareHasStockVo.setHasStock(false);
                wareHasStockVo.setSkuId(skuId);
            }
            return wareHasStockVo;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    @Transactional
    public boolean orderLockStock(WareSkuLockVo wareSkuLockVo) {
        WareOrderTaskEntity wareOrderTaskEntity = new WareOrderTaskEntity();
        wareOrderTaskEntity.setOrderSn(wareSkuLockVo.getOrderSn());
        wareOrderTaskService.save(wareOrderTaskEntity);
        List<OrderItemVo> locks = wareSkuLockVo.getLocks();
        List<SkuWareHasStock> collect = locks.stream().map(item -> {
            SkuWareHasStock stock = new SkuWareHasStock();
            Long skuId = item.getSkuId();
            stock.setSkuId(skuId);
            stock.setNum(item.getCount());
            List<Long> wareIdList = this.baseMapper.listWareIdHasSkuStock(skuId);
            stock.setWareId(wareIdList);
            return stock;
        }).collect(Collectors.toList());
        //订单里的每一个库存充足的sku都会被锁库存。
        for (SkuWareHasStock hasStock: collect
             ) {
            boolean skuStocked = false;
            Long skuId = hasStock.getSkuId();
            List<Long> wareIds = hasStock.getWareId();
            if (StringUtils.isEmpty(wareIds)){
                throw new NoStockException(skuId);
            }
            for (Long wareId:wareIds
                 ) {
                Long count = this.baseMapper.lockSkuStock(skuId,wareId,hasStock.getNum());
                if (count == 1){
                    skuStocked = true;
                    WareOrderTaskDetailEntity wa = WareOrderTaskDetailEntity.builder().skuId(skuId).skuName("").skuNum(hasStock.getNum()).taskId(wareOrderTaskEntity.getId()).wareId(wareId).lockStatus(1).build();
                    wareOrderTaskDetailService.save(wa);
                    StockLockedTo stockLockedTo = new StockLockedTo();
                    stockLockedTo.setId(wareOrderTaskEntity.getId());
                    StockDetailTo stockDetailTo = new StockDetailTo();
                    BeanUtils.copyProperties(wa,stockDetailTo);
                    stockLockedTo.setDetailTo(stockDetailTo);
                    rabbitTemplate.convertAndSend("stock-event-exchange","stock.locked",stockLockedTo);
                    break;
                }else {

                }
            }
            if (skuStocked ==false){
                throw new NoStockException(skuId);
            }
        }
        return true;
    }

    @Override
    public void unlockStock(StockLockedTo to){
        StockDetailTo detailTo = to.getDetailTo();
        Long detailToId = detailTo.getId();

        WareOrderTaskDetailEntity taskDetailEntity = wareOrderTaskDetailService.getById(detailToId);
        if (taskDetailEntity != null){
            Long id = to.getId();
            WareOrderTaskEntity orderTaskEntity = wareOrderTaskService.getById(id);
            String orderSn = orderTaskEntity.getOrderSn();
            R r = orderFeignClient.getOrderStatus(orderSn);
            if (r.getCode() == 0){
                OrderTo orderTo = r.getData(new TypeReference<OrderTo>() {
                });
                if (orderTo ==null||orderTo.getStatus()==4){
                    if (taskDetailEntity.getLockStatus() ==1){
                        unLockStock(detailTo.getSkuId(),detailTo.getWareId(),detailTo.getSkuNum(),detailToId);
                    }
                }
            }else {
                throw new RuntimeException("orderFeignClient.getOrderStatus(orderSn)远程调用异常");
            }
        }else {

        }

    }
    @Override
    @Transactional
    public void unlockStock(OrderTo orderTo){
        String orderSn = orderTo.getOrderSn();
        WareOrderTaskEntity wareOrderTaskEntity = wareOrderTaskService.getOne(new QueryWrapper<WareOrderTaskEntity>().eq("order_sn", orderSn));
        if (wareOrderTaskEntity != null){
            Long id = wareOrderTaskEntity.getId();
            List<WareOrderTaskDetailEntity> list = wareOrderTaskDetailService.list(new QueryWrapper<WareOrderTaskDetailEntity>().eq("task_id", id).eq("lock_status", 1));
            if (list!=null&&list.size()>0){
                for (WareOrderTaskDetailEntity item: list
                     ) {
                    if (item.getLockStatus() ==1)
                    unLockStock(item.getSkuId(),item.getWareId(),item.getSkuNum(),item.getId());
                }
            }
        }else {
            throw new RuntimeException("库存工作单异常丢失");
        }
    }



    private void unLockStock(Long skuId,Long wareId,Integer skuNum,Long detailId){
        this.baseMapper.unLockStock(skuId,skuNum,wareId);
        WareOrderTaskDetailEntity wareOrderTaskDetailEntity = new WareOrderTaskDetailEntity();
        wareOrderTaskDetailEntity.setId(detailId);
        wareOrderTaskDetailEntity.setLockStatus(2);
        wareOrderTaskDetailService.updateById(wareOrderTaskDetailEntity);
    }

    @Data
    class SkuWareHasStock {
        private Long skuId;
        private Integer num;
        private List<Long> wareId;
    }

}