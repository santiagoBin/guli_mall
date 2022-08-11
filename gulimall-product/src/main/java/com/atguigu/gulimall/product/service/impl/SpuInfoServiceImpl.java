package com.atguigu.gulimall.product.service.impl;

import com.atguigu.gulimall.common.to.SkuReductionTo;
import com.atguigu.gulimall.product.entity.*;
import com.atguigu.gulimall.common.to.SpuBoundTo;
import com.atguigu.gulimall.product.entity.vo.*;
import com.atguigu.gulimall.product.feign.CouponFeignClient;
import com.atguigu.gulimall.product.service.*;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.common.utils.Query;

import com.atguigu.gulimall.product.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {
    @Autowired
    SpuInfoDescService spuInfoDescService;
    @Autowired
    SpuImagesService spuImagesService;
    @Autowired
    AttrService attrService;
    @Autowired
    ProductAttrValueService productAttrValueService;
    @Autowired
    CouponFeignClient couponFeignClient;
    @Autowired
    SkuInfoService skuInfoService;
    @Autowired
    SkuImagesService skuImagesService;
    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Override
    public PageUtils

    queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>().eq(!StringUtils.isEmpty(params.get("catelogId")),"catelog_id",params.get("catelogId"))
                .eq(!StringUtils.isEmpty(params.get("brandId")),"brand_id",params.get("brandId"))
                .eq(!StringUtils.isEmpty(params.get("status")),"publish_status",params.get("status"))
                .like(!StringUtils.isEmpty(params.get("key")),"spu_name",params.get("key"))
        );

        return new PageUtils(page);
    }
    @Transactional
    @Override
    public void saveSpu(SpuSaveVo spuInfo) {
        //1、保存spu基本信息：pms_spu_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuInfo,spuInfoEntity);
        this.save(spuInfoEntity);
        //2、保存spu的描述图片：pms_spu_info_desc
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        List<String> desc = spuInfo.getDecript();
        String join = String.join(",", desc);
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        spuInfoDescEntity.setDecript(join);
        spuInfoDescService.save(spuInfoDescEntity);

        //3、保存spu的图片集：pms_spu_images
        List<String> images = spuInfo.getImages();
        if (images!=null&&images.size()>0){
            List<SpuImagesEntity> collect = images.stream().map(item -> {
                SpuImagesEntity spuImagesEntity = new SpuImagesEntity();
                spuImagesEntity.setSpuId(spuInfoEntity.getId());
                spuImagesEntity.setImgUrl(item);
                return spuImagesEntity;
            }).collect(Collectors.toList());
            spuImagesService.saveBatch(collect);
        }

        //4、保存spu的规格参数：pms_product_attr_value
        List<BaseAttrs> baseAttrs = spuInfo.getBaseAttrs();
        if (baseAttrs!=null && baseAttrs.size()>0){
            List<ProductAttrValueEntity> attrValueEntities = baseAttrs.stream().map(item -> {
                ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
                productAttrValueEntity.setSpuId(spuInfoEntity.getId());
                productAttrValueEntity.setAttrId(item.getAttrId());
                productAttrValueEntity.setAttrValue(item.getAttrValues());
                AttrEntity attrEntity = attrService.getById(item.getAttrId());
                productAttrValueEntity.setAttrName(attrEntity.getAttrName());
                productAttrValueEntity.setQuickShow(item.getShowDesc());
                return productAttrValueEntity;
            }).collect(Collectors.toList());
            productAttrValueService.saveBatch(attrValueEntities);
        }

        //5、保存spu的积分信息：gulimall_sms--->sms_spu_bounds
        Bounds bounds = spuInfo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtils.copyProperties(bounds,spuBoundTo);
        spuBoundTo.setSpuId(spuInfoEntity.getId());
        couponFeignClient.save(spuBoundTo);

        //6、保存当前spu对应的所有sku信息：pms_sku_info
        //6、1）、sku的基本信息:pms_sku_info
        List<Skus> skus = spuInfo.getSkus();
        if (skus!=null&&skus.size()>0){
            skus.forEach(sku->{
                String defaultImg="";
                for (Images img:sku.getImages()) {
                    if (img.getDefaultImg() == 1){
                        defaultImg = img.getImgUrl();
                    }
                }
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(sku,skuInfoEntity);
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatelogId(spuInfoEntity.getCatelogId());
                skuInfoEntity.setSkuDefaultImg(defaultImg);
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoService.save(skuInfoEntity);
                Long skuId = skuInfoEntity.getSkuId();
                if (sku.getImages()!=null&&sku.getImages().size()>0){
                    List<SkuImagesEntity> skuImagesEntities = sku.getImages().stream().map(img -> {
                        SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                        skuImagesEntity.setSkuId(skuId);
                        skuImagesEntity.setImgUrl(img.getImgUrl());
                        skuImagesEntity.setDefaultImg(img.getDefaultImg());
                        return skuImagesEntity;
                    }).filter(item -> {
                        return !StringUtils.isEmpty(item.getImgUrl());
                    }).collect(Collectors.toList());
                    if (skuImagesEntities!=null&&skuImagesEntities.size()>0){
                        //6、2）、sku的图片信息：pms_sku_images
                        skuImagesService.saveBatch(skuImagesEntities);
                    }
                }
                //6、3）、sku的销售属性：pms_sku_sale_attr_value
                List<Attr> attrs = sku.getAttr();
                if (attrs!=null&&attrs.size()>0){
                    List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attrs.stream().map(attr -> {
                        SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                        skuSaleAttrValueEntity.setSkuId(skuId);
                        BeanUtils.copyProperties(attr, skuSaleAttrValueEntity);
                        return skuSaleAttrValueEntity;
                    }).collect(Collectors.toList());
                    if (skuSaleAttrValueEntities.size()>0){
                        skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);
                    }
                }
                //6、4）、sku的优惠、满减等信息：gulimall_sms--->sms_sku_ladder、sms_sku_full_reduction、sms_member_price
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(sku,skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                if (skuReductionTo.getFullCount()>0&&skuReductionTo.getFullPrice().compareTo(BigDecimal.ZERO)==1){
                    couponFeignClient.saveInfo(skuReductionTo);
                }
            });
        }
    }

    @Override
    public void changeSpuStatus(String id) {
        UpdateWrapper<SpuInfoEntity> spuInfoEntityUpdateWrapper = new UpdateWrapper<>();
        spuInfoEntityUpdateWrapper.eq("id",id).set("publish_status",1);
        this.update(spuInfoEntityUpdateWrapper);
    }

    @Override
    public void changeSpuStatusToDown(String id) {
        UpdateWrapper<SpuInfoEntity> spuInfoEntityUpdateWrapper = new UpdateWrapper<>();
        spuInfoEntityUpdateWrapper.eq("id",id).set("publish_status",2);
        this.update(spuInfoEntityUpdateWrapper);
    }
}