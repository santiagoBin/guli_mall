package com.atguigu.gulimall.order.feign;

import com.atguigu.gulimall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("gulimall-product")
public interface ProductFeignClient {
    @GetMapping("product/spuinfo/getSpuInfoBySkuId")
    public R getSpuInfoBySkuId(@RequestParam("skuId") Long skuId);

    @RequestMapping("product/skuinfo//info/{skuId}")
    //@RequiresPermissions("product:skuinfo:info")
    public R info(@PathVariable("skuId") Long skuId);
}
