package com.atguigu.gulimall.order.web;

import com.alipay.api.AlipayApiException;
import com.atguigu.gulimall.order.config.AlipayTemplate;
import com.atguigu.gulimall.order.entity.OrderEntity;
import com.atguigu.gulimall.order.service.OrderService;
import com.atguigu.gulimall.order.vo.PayVo;
import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import java.util.Map;

import static com.lly835.bestpay.enums.BestPayTypeEnum.WXPAY_NATIVE;

@Controller
@Slf4j
public class PayWebController {
    @Autowired
    OrderService orderService;
    @Autowired
    AlipayTemplate alipayTemplate;
    @Autowired
    BestPayService bestPayService;

    @Resource
    private WxPayConfig wxPayConfig;

    @ResponseBody
    @GetMapping(value = "/aliPayOrder",produces = "text/html")
    public String aliPayOrder(@RequestParam("orderSn") String orderSn) throws AlipayApiException {
        PayVo payVo = orderService.getOrderPay(orderSn);
        String pay = alipayTemplate.pay(payVo);

        return pay;
    }

    /**
     * 微信支付
     * @param orderSn
     * @return
     */
    @GetMapping(value = "/weixinPayOrder")
    public String createNative(@RequestParam("orderSn") String orderSn,Model model) {
        OrderEntity orderInfo = orderService.getOrderByOrderSn(orderSn);
        Map<String,String> map = orderService.createNative(orderInfo.getOrderSn32BitForWxPay());
        model.addAllAttributes(map);
        return "createForWxNative";
    }







    //    @GetMapping(value = "/weixinPayOrder")
//    public String weixinPayOrder(@RequestParam("orderSn") String orderSn, Model model) {
//
//        OrderEntity orderInfo = orderService.getOrderByOrderSn(orderSn);
//
//        if (orderInfo == null) {
//            throw new RuntimeException("订单不存在");
//        }
//
//        PayRequest request = new PayRequest();
//        request.setOrderName("4559066-最好的支付sdk");
//        request.setOrderId(orderInfo.getOrderSn32BitForWxPay());
//        request.setOrderAmount(0.01);
//        request.setPayTypeEnum(WXPAY_NATIVE);
//
//        PayResponse payResponse = bestPayService.pay(request);
//        payResponse.setOrderId(orderInfo.getOrderSn());
//        log.info("发起支付 response={}", payResponse);
//
//        //传入前台的二维码路径生成支付二维码
//        model.addAttribute("codeUrl",payResponse.getCodeUrl());
//        model.addAttribute("orderId",payResponse.getOrderId());
//        model.addAttribute("returnUrl",wxPayConfig.getReturnUrl());
//
//        return "createForWxNative";
//    }


    //根据订单号查询订单状态的API
    @GetMapping(value = "/queryByOrderId")
    @ResponseBody
    public OrderEntity queryByOrderId(@RequestParam("orderId") String orderId) {
        log.info(orderId);
        log.info("查询支付记录...");
        return orderService.getOrderByOrderSn(orderId);
    }
}