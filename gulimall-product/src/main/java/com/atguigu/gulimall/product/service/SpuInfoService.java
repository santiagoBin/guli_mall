package com.atguigu.gulimall.product.service;

import com.atguigu.gulimall.common.to.product.SpuInfoTo;
import com.atguigu.gulimall.product.entity.vo.SpuSaveVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.product.entity.SpuInfoEntity;

import java.util.Map;

/**
 * spu信息
 *
 * @author santiago
 * @email 2457039825@qq.com
 * @date 2022-07-18 21:38:21
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpu(SpuSaveVo spuInfo);

    void changeSpuStatus(String id);

    void changeSpuStatusToDown(String id);

    void up(Long spuId);

    SpuInfoTo getSpuInfoBySkuId(Long skuId);
}

