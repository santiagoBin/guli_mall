package com.atguigu.gulimall.order.feign;

import com.atguigu.gulimall.common.to.cart.CartItemInOrderTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("gulimall-cart")
public interface CartFeignClient {
    @GetMapping("getUserCurrentCartItems")
    public List<CartItemInOrderTo> getUserCurrentCartItems();
}
