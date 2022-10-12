package com.atguigu.gulimall.cart.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@FeignClient("gulimall-product")
public interface ProductFeignClient {
    @GetMapping("product/skuinfo/getSkuInfoBySkuId")
    public com.atguigu.gulimall.common.to.cart.SkuInfoTo getSkuInfoBySkuId(@RequestParam("skuId") Long skuId);

    @GetMapping("product/skusaleattrvalue/getSkuSaleAttrListBySkuId")
    public List<String> getSkuSaleAttrListBySkuId(@RequestParam("skuId") Long skuId);

    @GetMapping(value = "product/skuinfo/{skuId}/price")
    public BigDecimal getPrice(@PathVariable("skuId") Long skuId);
}
