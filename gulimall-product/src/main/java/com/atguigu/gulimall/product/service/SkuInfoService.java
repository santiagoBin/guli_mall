package com.atguigu.gulimall.product.service;

import com.atguigu.gulimall.common.to.SkuInfoTo;
import com.atguigu.gulimall.product.entity.vo.SkuItemVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.product.entity.SkuInfoEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * sku信息
 *
 * @author santiago
 * @email 2457039825@qq.com
 * @date 2022-07-18 21:38:21
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void removeSkuByIds(List<Long> asList);

    SkuInfoTo getSkuInfoToById(Long skuId);

    List<SkuInfoEntity> getSkusBySpuId(Long spuId);

    SkuItemVo item(Long skuId) throws ExecutionException, InterruptedException;
}

