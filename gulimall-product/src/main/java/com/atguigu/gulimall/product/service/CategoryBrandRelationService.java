package com.atguigu.gulimall.product.service;

import com.atguigu.gulimall.product.entity.vo.AttrGroupCateRelationVo;
import com.atguigu.gulimall.product.entity.vo.BrandVo;
import com.atguigu.gulimall.product.entity.vo.CatelogVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.product.entity.CategoryBrandRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author santiago
 * @email 2457039825@qq.com
 * @date 2022-07-18 21:38:21
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

    void updateCategory(Long catId, String name);

    List<BrandVo> getCateRelationBrands(String catId);

    List<CatelogVo> getBrandRelationCategory(String brandId);

    List<AttrGroupCateRelationVo> getCateRelationAttrGroupAndAttrs(String catelogId);

    void removeRelation(CatelogVo catelogVo);
}

