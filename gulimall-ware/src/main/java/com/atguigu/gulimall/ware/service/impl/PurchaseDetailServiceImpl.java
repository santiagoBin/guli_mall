package com.atguigu.gulimall.ware.service.impl;

import com.atguigu.gulimall.common.to.SkuInfoTo;
import com.atguigu.gulimall.ware.feign.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.common.utils.Query;

import com.atguigu.gulimall.ware.dao.PurchaseDetailDao;
import com.atguigu.gulimall.ware.entity.PurchaseDetailEntity;
import com.atguigu.gulimall.ware.service.PurchaseDetailService;
import org.springframework.util.StringUtils;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {
    @Autowired
    ProductFeignClient productFeignClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                new QueryWrapper<PurchaseDetailEntity>().eq(!StringUtils.isEmpty(params.get("wareId")),"ware_id",params.get("wareId"))
                .eq(!StringUtils.isEmpty(params.get("status")),"status",params.get("status"))
                .like(!StringUtils.isEmpty(params.get("key")),"sku_name",params.get("key"))
        );

        return new PageUtils(page);
    }

    @Override
    public void savePurchaseDetail(PurchaseDetailEntity purchaseDetail) {
        SkuInfoTo skuInfoTo = productFeignClient.getSkuById(purchaseDetail.getSkuId());
        purchaseDetail.setSkuPrice(skuInfoTo.getPrice().multiply(BigDecimal.valueOf(purchaseDetail.getSkuNum().intValue())));
        purchaseDetail.setSkuName(skuInfoTo.getSkuName());
        this.save(purchaseDetail);
    }

}