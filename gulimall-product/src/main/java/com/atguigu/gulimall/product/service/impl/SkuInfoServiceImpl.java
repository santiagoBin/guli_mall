package com.atguigu.gulimall.product.service.impl;

import com.atguigu.gulimall.common.to.SkuInfoTo;
import com.atguigu.gulimall.product.entity.SkuImagesEntity;
import com.atguigu.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.atguigu.gulimall.product.feign.CouponFeignClient;
import com.atguigu.gulimall.product.service.SkuImagesService;
import com.atguigu.gulimall.product.service.SkuSaleAttrValueService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.common.utils.Query;

import com.atguigu.gulimall.product.dao.SkuInfoDao;
import com.atguigu.gulimall.product.entity.SkuInfoEntity;
import com.atguigu.gulimall.product.service.SkuInfoService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {
    @Autowired
    SkuImagesService skuImagesService;
    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    CouponFeignClient couponFeignClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>().eq(!StringUtils.isEmpty(params.get("catelogId")),"catelog_id",params.get("catelogId"))
                .eq(!StringUtils.isEmpty(params.get("brandId")),"brand_id",params.get("brandId"))
                .like(!StringUtils.isEmpty(params.get("key")),"sku_name",params.get("key"))
                .le(!StringUtils.isEmpty(params.get("max")),"price",params.get("max"))
                .ge(!StringUtils.isEmpty(params.get("min")),"price",params.get("min"))
        );
        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void removeSkuByIds(List<Long> skuIds) {
        if (skuIds!=null&&skuIds.size()>0){
            this.removeByIds(skuIds);
            skuImagesService.remove(new QueryWrapper<SkuImagesEntity>().in("sku_id",skuIds));
            skuSaleAttrValueService.remove(new QueryWrapper<SkuSaleAttrValueEntity>().in("sku_id",skuIds));
            couponFeignClient.deleteSkuLadderBySkuIds(skuIds);
            couponFeignClient.deleteSkuFullReductionBySkuIds(skuIds);
        }
    }

    @Override
    public SkuInfoTo getSkuInfoToById(Long skuId) {
        SkuInfoEntity byId = this.getById(skuId);
        SkuInfoTo skuInfoTo = new SkuInfoTo();
        BeanUtils.copyProperties(byId,skuInfoTo);
        return skuInfoTo;
    }

}