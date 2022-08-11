package com.atguigu.gulimall.ware.feign;

import com.atguigu.gulimall.common.to.SkuInfoTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("gulimall-product")
@Controller
public interface ProductFeignClient {
    @GetMapping("product/skuinfo/getSkuById")
    public SkuInfoTo getSkuById(@RequestParam("skuId") Long skuId);
}
