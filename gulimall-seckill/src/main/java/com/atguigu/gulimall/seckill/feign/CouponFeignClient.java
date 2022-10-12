package com.atguigu.gulimall.seckill.feign;

import com.atguigu.gulimall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("gulimall-coupon")
public interface CouponFeignClient {
    @GetMapping("coupon/seckillsession/Latest3DaySession")
    public R getLatest3DaySession();
}
