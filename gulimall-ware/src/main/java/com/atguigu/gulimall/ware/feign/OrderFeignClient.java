package com.atguigu.gulimall.ware.feign;

import com.atguigu.gulimall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("gulimall-order")
public interface OrderFeignClient {
    @GetMapping("order/order/status/{orderSn}")
    public R getOrderStatus(@PathVariable("orderSn") String orderSn);

}
