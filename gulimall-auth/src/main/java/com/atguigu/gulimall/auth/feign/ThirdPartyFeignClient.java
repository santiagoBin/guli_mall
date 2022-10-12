package com.atguigu.gulimall.auth.feign;

import com.atguigu.gulimall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("gulimall-third-party")
@Controller
public interface ThirdPartyFeignClient {
    @GetMapping("/sms/sendcode")
    public R sendCode(@RequestParam("phone") String phone);
}
