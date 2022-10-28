package com.atguigu.gulimall.thirdparty.controller;

import com.atguigu.gulimall.common.utils.Constant;
import com.atguigu.gulimall.common.utils.HttpUtils;
import com.atguigu.gulimall.common.utils.R;
import com.atguigu.gulimall.common.utils.RandomUtil;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("sms")
public class SmsController {
//    @Value("${spring.aliyun.sms.host}")
    private String host = "https://gyytz.market.alicloudapi.com";
//    @Value("${spring.aliyun.sms.path}")
    private String path = "/sms/smsSend";
//    @Value("${spring.aliyun.sms.method}")
    private String method = "post";
//    @Value("${spring.aliyun.sms.appcode}")
    private String appcode = "2022fd3db7464a0d904a81195bc7e3b8";
    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("sendcode")
    public R sendCode(@RequestParam("phone") String phone){
        String existCode =  redisTemplate.opsForValue().get(Constant.REGISTER_CODE_PREFIX_KEY+phone);
        if (StringUtils.isEmpty(existCode)){
            R r = send(phone);
            return r;
        }else {
            String timeStr = existCode.substring(existCode.indexOf("_")+1);
            long time = Long.parseLong(timeStr);
            if (System.currentTimeMillis() - time > 60*1000){
                R r = send(phone);
                return r;
            }else {
                return R.error("验证码发送过于频繁！");
            }
        }
    }

    private R send(String phone) {
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phone);
        String code = RandomUtil.getFourBitRandom();
        querys.put("param", "**code**:"+code+",**minute**:5");
        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");
        Map<String, String> bodys = new HashMap<String, String>();
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            redisTemplate.opsForValue().set(Constant.REGISTER_CODE_PREFIX_KEY+phone,code+"_"+System.currentTimeMillis(),5, TimeUnit.MINUTES);
            return R.ok("验证码发送成功！请注意查收。");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("验证码发送失败，请重试。");
        }
    }
}
