package com.atguigu.gulimall.product.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.atguigu.gulimall.common.constant.ProductConstant;
import com.atguigu.gulimall.common.to.SkuReductionTo;
import com.atguigu.gulimall.common.to.WareHasStockTo;
import com.atguigu.gulimall.common.to.es.SkuEsModel;
import com.atguigu.gulimall.common.to.product.SpuInfoTo;
import com.atguigu.gulimall.common.utils.R;
import com.atguigu.gulimall.product.entity.*;
import com.atguigu.gulimall.common.to.SpuBoundTo;
import com.atguigu.gulimall.product.entity.vo.*;
import com.atguigu.gulimall.product.feign.CouponFeignClient;
import com.atguigu.gulimall.product.feign.SearchFeignClient;
import com.atguigu.gulimall.product.feign.WareFeignClient;
import com.atguigu.gulimall.product.service.*;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.common.utils.Query;

import com.atguigu.gulimall.product.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Slf4j
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
    @Autowired
    BrandService brandService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    WareFeignClient wareFeignClient;
    @Autowired
    SearchFeignClient searchFeignClient;



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

    @Override
    public void up(Long spuId) {
        List<SkuEsModel> upProducts = new ArrayList<>();
        //组装需要的数据
        //1、查出当前spuid对应的所有的sku信息，品牌的名字
        List<SkuInfoEntity> skus = skuInfoService.getSkusBySpuId(spuId);
        List<Long> skuIds = skus.stream().map(SkuInfoEntity::getSkuId).collect(Collectors.toList());
        //TODO 4、查询当前sku所有的可以用于检索的规格属性
        List<ProductAttrValueEntity> productAttrValueEntities = productAttrValueService.list(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));
        List<Long> attrIds = productAttrValueEntities.stream().map(productAttrValueEntity -> {
            return productAttrValueEntity.getAttrId();
        }).collect(Collectors.toList());
        List<Long> searchIds = attrService.getSearchAttrByIds(attrIds);
        HashSet<Long> idSet = new HashSet<>(searchIds);
        ArrayList<SkuEsModel.Attrs> attrs = new ArrayList<>();
        List<SkuEsModel.Attrs> attrsList = productAttrValueEntities.stream().filter(item -> {
            return idSet.contains(item.getAttrId());
        }).map(item -> {
            SkuEsModel.Attrs attr = new SkuEsModel.Attrs();
            BeanUtils.copyProperties(item, attr);
            return attr;
        }).collect(Collectors.toList());
        //TODO 1、发送远程调用，库存系统查询是否有库存
        Map<Long,Boolean> stockMap =null;
        try{
            R r = wareFeignClient.getSkuHasStock(skuIds);
            TypeReference<List<WareHasStockTo>> typeReference = new TypeReference<List<WareHasStockTo>>() {
            };
            List<WareHasStockTo> data = r.getData(typeReference);
            stockMap = data.stream().collect(Collectors.toMap(WareHasStockTo::getSkuId, item -> item.getHasStock()));
        }catch (Exception e){
            log.error("库存服务查询异常：原因{}",e);
        }

        Map<Long, Boolean> finalStockMap = stockMap;
        upProducts = skus.stream().map(sku -> {
            SkuEsModel skuEsModel = new SkuEsModel();
            BeanUtils.copyProperties(sku, skuEsModel);
            skuEsModel.setSkuPrice(sku.getPrice());
            skuEsModel.setSkuImg(sku.getSkuDefaultImg());
            if (finalStockMap == null) {
                skuEsModel.setHasStock(true);
            } else {
                skuEsModel.setHasStock(finalStockMap.get(sku.getSkuId()));
            }
            //TODO 2、热度评分
            skuEsModel.setHotScore(0l);
            //TODO 3、查询品牌和分类的名字信息
            BrandEntity brand = brandService.getById(skuEsModel.getBrandId());
            skuEsModel.setBrandName(brand.getName());
            skuEsModel.setBrandImg(brand.getLogo());
            CategoryEntity category = categoryService.getById(skuEsModel.getCatelogId());
            skuEsModel.setCatelogName(category.getName());
            //设置检索属性
            skuEsModel.setAttrs(attrsList);
            return skuEsModel;
        }).collect(Collectors.toList());
        R r = searchFeignClient.productStatusUp(upProducts);
        if (r.getCode()==0){
            UpdateWrapper<SpuInfoEntity> spuInfoEntityUpdateWrapper = new UpdateWrapper<>();
            spuInfoEntityUpdateWrapper.eq("id",spuId).set("publish_status", ProductConstant.ProductStatusEnum.SPU_UP.getCode()).set("update_time",LocalDateTime.now());
            this.update(spuInfoEntityUpdateWrapper);
        }else {

        }
    }

    @Override
    public SpuInfoTo getSpuInfoBySkuId(Long skuId) {
        SkuInfoEntity skuInfoEntity = skuInfoService.getOne(new QueryWrapper<SkuInfoEntity>().eq("sku_id", skuId));
        SpuInfoEntity spuInfoEntity = this.getById(skuInfoEntity.getSpuId());
        BrandEntity brand = brandService.getOne(new QueryWrapper<BrandEntity>().eq("brand_id", skuInfoEntity.getBrandId()));
        SpuInfoTo spuInfoTo = new SpuInfoTo();
        BeanUtils.copyProperties(spuInfoEntity,spuInfoTo);
        spuInfoTo.setBrandName(brand.getName());
        return spuInfoTo;
    }
}