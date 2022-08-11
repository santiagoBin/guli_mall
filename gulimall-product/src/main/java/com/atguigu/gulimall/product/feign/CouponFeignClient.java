package com.atguigu.gulimall.product.feign;


import com.atguigu.gulimall.common.to.SkuReductionTo;
import com.atguigu.gulimall.common.utils.R;
import com.atguigu.gulimall.common.to.SpuBoundTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@FeignClient("gulimall-coupon")
@Controller
public interface CouponFeignClient {
    @RequestMapping("/coupon/spubounds/saveSpuBounds")
    public R save(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("coupon/skufullreduction/saveInfo")
    public R saveInfo(@RequestBody SkuReductionTo skuReductionTo);

    @RequestMapping("coupon/skuladder/deleteBySkuIds")
    //@RequiresPermissions("coupon:skuladder:delete")
    public R deleteSkuLadderBySkuIds(@RequestBody List<Long> ids);

    @RequestMapping("coupon/skufullreduction/deleteBySkuIds")
    //@RequiresPermissions("coupon:skufullreduction:delete")
    public R deleteSkuFullReductionBySkuIds(@RequestBody List<Long> ids);
}
