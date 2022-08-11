package com.atguigu.gulimall.ware.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.atguigu.gulimall.ware.entity.vo.PurchaseDoneVo;
import com.atguigu.gulimall.ware.entity.vo.PurchaseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.ware.entity.PurchaseEntity;
import com.atguigu.gulimall.ware.service.PurchaseService;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.common.utils.R;



/**
 * 采购信息
 *
 * @author santiago
 * @email 2457039825@qq.com
 * @date 2022-07-18 23:01:05
 */
@RestController
@RequestMapping("ware/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("ware:purchase:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("ware:purchase:info")
    public R info(@PathVariable("id") Long id){
		PurchaseEntity purchase = purchaseService.getById(id);

        return R.ok().put("purchase", purchase);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
   // @RequiresPermissions("ware:purchase:save")
    public R save(@RequestBody PurchaseEntity purchase){
		purchaseService.save(purchase);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("ware:purchase:update")
    public R update(@RequestBody PurchaseEntity purchase){
		purchaseService.updateById(purchase);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("ware:purchase:delete")
    public R delete(@RequestBody Long[] ids){
		purchaseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @GetMapping("unreceive/list")
    public R getUnreceiveList(){
        PageUtils pageUtils = purchaseService.getUnreceiveList();
        return R.ok().put("page",pageUtils);
    }

    @PostMapping("merge")
    public R mergePurchase(@RequestBody PurchaseVo purchaseVo){
        purchaseService.mergePurchase(purchaseVo);
        return R.ok();
    }

    @PostMapping("received")
    public R receivePurchase(@RequestBody ArrayList<Long> purchaseIds){
        purchaseService.receivePurchase(purchaseIds);
        return R.ok();
    }

    @PostMapping("done")
    public R purchaseDone(@RequestBody PurchaseDoneVo purchaseDoneVo){
        purchaseService.purchaseDone(purchaseDoneVo);
        return R.ok();
    }
}
