package com.atguigu.gulimall.order.listener;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.atguigu.gulimall.order.config.AlipayTemplate;
import com.atguigu.gulimall.order.config.PayConfig;
import com.atguigu.gulimall.order.service.OrderService;
import com.atguigu.gulimall.order.vo.PayAsyncVo;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.lly835.bestpay.config.WxPayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@Slf4j
public class OrderPayedListener {
    @Autowired
    AlipayTemplate alipayTemplate;
    @Autowired
    OrderService orderService;

    @PostMapping("payed/notify")
    public String handleAlipayed(PayAsyncVo asyncVo, HttpServletRequest request) throws AlipayApiException {
        log.info(asyncVo.toString());
        HashMap<String, String> params = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<String> set = parameterMap.keySet();
        for (String name: set){
            String[] values = parameterMap.get(name);
            String valueStr = "";
            for (int i = 0 ;i<values.length;i++){
                valueStr = (i == values.length-1)?valueStr+values[i]:valueStr+values[i]+",";
            }
            params.put(name,valueStr);
        }
        System.out.println("验签开始");
        boolean b = AlipaySignature.rsaCheckV1(params, alipayTemplate.getAlipay_public_key(), alipayTemplate.getCharset(), alipayTemplate.getSign_type());
        System.out.println("验签结束");
        if (b){
            System.out.println("签名验证成功...");
            String result = orderService.handlePayResult(asyncVo);
            return result;
        }else {
            System.out.println("签名验证失败...");
            return "error";
        }
    }

    @PostMapping(value = "/pay/notify")
    public String asyncNotify(@RequestBody String notifyData) {
        //异步通知结果
        return orderService.asyncNotify(notifyData);
    }

    @PostMapping("wxPay/notify")
    public String wxnotify(HttpServletRequest request, HttpServletResponse response) {
        String xmlBack = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while((line = br.readLine()) != null){
                sb.append(line);
            }
            br.close();
            //sb为微信返回的xml，微信sdk提供转化成map的方法
            String notityXml = sb.toString();
            System.out.println("接收到的报文：" + notityXml);

            Map<String, String> map =  WXPayUtil.xmlToMap(notityXml);
            WXPay wxpay = new WXPay(new PayConfig());
            if (wxpay.isPayResultNotifySignatureValid(map)) {//验证成功
                String return_code = map.get("return_code");//状态
                String out_trade_no = map.get("out_trade_no");//订单号
                String result_code = map.get("result_code");
                //其实这里验证两个code都是success就说明充值成功了。可以处理自己的业务逻辑了。
                if (!"SUCCESS".equals(return_code) || !"SUCCESS".equals(result_code)) {
                    System.out.println("充值失败！");
                    //支付失败的逻辑
                    xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                    return xmlBack;
                }
                System.out.println("out_trade_no:"+out_trade_no);

                xmlBack = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[SUCCESS]]></return_msg>" + "</xml> ";

                return xmlBack;
            } else {
                System.out.println("<<<<<<<<<<<<<<<<<<<<<微信支付回调通知签名错误");
                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                return xmlBack;
            }
        } catch (Exception e) {
            System.err.println(e);
            System.out.println("微信支付回调通知失败");
            xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            return xmlBack;
        }
    }
}
