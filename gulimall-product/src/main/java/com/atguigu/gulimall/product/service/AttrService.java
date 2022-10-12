package com.atguigu.gulimall.product.service;

import com.atguigu.gulimall.product.entity.ProductAttrValueEntity;
import com.atguigu.gulimall.product.entity.vo.AttrRespVo;
import com.atguigu.gulimall.product.entity.vo.AttrVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.product.entity.AttrEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author santiago
 * @email 2457039825@qq.com
 * @date 2022-07-18 21:38:20
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, String cateId,String type);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    List<AttrEntity> getAttrRelation(Long attrgroupId);

    List<ProductAttrValueEntity> getProductAttrValListForSpu(String spuId);

    void updateSpuAttr(Long spuId,List<ProductAttrValueEntity> attrs);

    List<Long> getSearchAttrByIds(List<Long> attrIds);
}

