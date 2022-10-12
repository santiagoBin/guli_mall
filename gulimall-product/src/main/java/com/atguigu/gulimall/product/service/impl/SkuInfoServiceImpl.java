package com.atguigu.gulimall.product.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.atguigu.gulimall.common.to.SkuInfoTo;
import com.atguigu.gulimall.common.utils.R;
import com.atguigu.gulimall.product.entity.SkuImagesEntity;
import com.atguigu.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.atguigu.gulimall.product.entity.SpuInfoDescEntity;
import com.atguigu.gulimall.product.entity.to.SeckillSkuRedisTo;
import com.atguigu.gulimall.product.entity.vo.SkuItemSaleAttrVo;
import com.atguigu.gulimall.product.entity.vo.SkuItemVo;
import com.atguigu.gulimall.product.entity.vo.SpuItemAttrGroupVo;
import com.atguigu.gulimall.product.feign.CouponFeignClient;
import com.atguigu.gulimall.product.feign.SeckillFeignClient;
import com.atguigu.gulimall.product.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.common.utils.Query;

import com.atguigu.gulimall.product.dao.SkuInfoDao;
import com.atguigu.gulimall.product.entity.SkuInfoEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {
    @Autowired
    SkuImagesService skuImagesService;
    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    CouponFeignClient couponFeignClient;
    @Resource
    ThreadPoolExecutor executor;
    @Autowired
    SpuInfoDescService spuInfoDescService;
    @Autowired
    AttrGroupService attrGroupService;
    @Autowired
    SeckillFeignClient seckillFeignClient;
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

    @Override
    public List<SkuInfoEntity> getSkusBySpuId(Long spuId) {
        List<SkuInfoEntity> skus = this.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));
        return skus;
    }

    @Override
    public SkuItemVo item(Long skuId) throws ExecutionException, InterruptedException {

        SkuItemVo skuItemVo = new SkuItemVo();

        CompletableFuture<SkuInfoEntity> infoFuture = CompletableFuture.supplyAsync(() -> {
            //1、sku基本信息的获取  pms_sku_info
            SkuInfoEntity info = this.getById(skuId);
            skuItemVo.setInfo(info);
            return info;
        }, executor);


        CompletableFuture<Void> saleAttrFuture = infoFuture.thenAcceptAsync((res) -> {
            //3、获取spu的销售属性组合
            List<SkuItemSaleAttrVo> saleAttrVos = skuSaleAttrValueService.getSaleAttrBySpuId(res.getSpuId());
            skuItemVo.setSaleAttr(saleAttrVos);
        }, executor);


        CompletableFuture<Void> descFuture = infoFuture.thenAcceptAsync((res) -> {
            //4、获取spu的介绍    pms_spu_info_desc
            SpuInfoDescEntity spuInfoDescEntity = spuInfoDescService.getById(res.getSpuId());
            skuItemVo.setDesc(spuInfoDescEntity);
        }, executor);


        CompletableFuture<Void> baseAttrFuture = infoFuture.thenAcceptAsync((res) -> {
            //5、获取spu的规格参数信息
            List<SpuItemAttrGroupVo> attrGroupVos = attrGroupService.getAttrGroupWithAttrsBySpuId(res.getSpuId(), res.getCatelogId());
            skuItemVo.setGroupAttrs(attrGroupVos);
        }, executor);


        // Long spuId = info.getSpuId();
        // Long catalogId = info.getCatalogId();

        //2、sku的图片信息    pms_sku_images
        CompletableFuture<Void> imageFuture = CompletableFuture.runAsync(() -> {
            List<SkuImagesEntity> imagesEntities = skuImagesService.getImagesBySkuId(skuId);
            skuItemVo.setImages(imagesEntities);
        }, executor);

        CompletableFuture<Void> seckillFuture = CompletableFuture.runAsync(() -> {
            //3、远程调用查询当前sku是否参与秒杀优惠活动
            R r = seckillFeignClient.getSkuSeckillInfo(skuId);
            if (r.getCode() == 0) {
                //查询成功
                SeckillSkuRedisTo data = r.getData(new TypeReference<SeckillSkuRedisTo>() {
                });
                skuItemVo.setSeckillSkuRedisTo(data);

                if (data != null) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime > data.getEndTime()) {
                        skuItemVo.setSeckillSkuRedisTo(null);
                    }
                }
            }
        }, executor);


        //等到所有任务都完成
        CompletableFuture.allOf(saleAttrFuture,descFuture,baseAttrFuture,imageFuture).get();

        return skuItemVo;
    }

}