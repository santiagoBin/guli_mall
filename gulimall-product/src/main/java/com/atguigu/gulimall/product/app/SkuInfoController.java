package com.atguigu.gulimall.product.app;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.atguigu.gulimall.common.to.SkuInfoTo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.product.entity.SkuInfoEntity;
import com.atguigu.gulimall.product.service.SkuInfoService;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.common.utils.R;



/**
 * sku信息
 *
 * @author santiago
 * @email 2457039825@qq.com
 * @date 2022-07-18 22:11:54
 */
@RestController
@RequestMapping("product/skuinfo")
public class SkuInfoController {
    @Autowired
    private SkuInfoService skuInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:skuinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = skuInfoService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{skuId}")
    //@RequiresPermissions("product:skuinfo:info")
    public R info(@PathVariable("skuId") Long skuId){
		SkuInfoEntity skuInfo = skuInfoService.getById(skuId);

        return R.ok().put("skuInfo", skuInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
   // @RequiresPermissions("product:skuinfo:save")
    public R save(@RequestBody SkuInfoEntity skuInfo){
		skuInfoService.save(skuInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:skuinfo:update")
    public R update(@RequestBody SkuInfoEntity skuInfo){
		skuInfoService.updateById(skuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:skuinfo:delete")
    public R delete(@RequestBody Long[] skuIds){
		skuInfoService.removeSkuByIds(Arrays.asList(skuIds));

        return R.ok();
    }

    @GetMapping("/getSkuById")
    public SkuInfoTo getSkuById(@RequestParam("skuId") Long skuId){
        SkuInfoTo skuInfoTo = skuInfoService.getSkuInfoToById(skuId);
        return skuInfoTo;
    }

    @GetMapping("/getSkuInfoBySkuId")
    public com.atguigu.gulimall.common.to.cart.SkuInfoTo getSkuInfoBySkuId(@RequestParam("skuId") Long skuId){
        com.atguigu.gulimall.common.to.cart.SkuInfoTo skuInfoTo = new com.atguigu.gulimall.common.to.cart.SkuInfoTo();
        SkuInfoEntity byId = skuInfoService.getById(skuId);
        BeanUtils.copyProperties(byId,skuInfoTo);
        return skuInfoTo;
    }

    @GetMapping(value = "/{skuId}/price")
    public BigDecimal getPrice(@PathVariable("skuId") Long skuId) {

        //获取当前商品的信息
        SkuInfoEntity skuInfo = skuInfoService.getById(skuId);

        //获取商品的价格
        BigDecimal price = skuInfo.getPrice();

        return price;
    }

}
