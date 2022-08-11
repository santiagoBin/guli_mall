package com.atguigu.gulimall.product.service.impl;

import com.atguigu.gulimall.product.entity.*;
import com.atguigu.gulimall.product.entity.vo.AttrGroupCateRelationVo;
import com.atguigu.gulimall.product.entity.vo.BrandVo;
import com.atguigu.gulimall.product.entity.vo.CatelogVo;
import com.atguigu.gulimall.product.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.common.utils.Query;

import com.atguigu.gulimall.product.dao.CategoryBrandRelationDao;
import org.springframework.util.StringUtils;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {
    @Autowired
    BrandService brandService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    AttrGroupService attrGroupService;
    @Autowired
    AttrAttrgroupRelationService attrAttrgroupRelationService;
    @Autowired
    AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCatelogId();
        if (brandId!=null&&catelogId!=null){
            BrandEntity brandEntity = brandService.getById(brandId);
            CategoryEntity categoryEntity = categoryService.getById(catelogId);
            categoryBrandRelation.setBrandName(brandEntity.getName());
            categoryBrandRelation.setCatelogName(categoryEntity.getName());
            this.save(categoryBrandRelation);
        }
    }

    @Override
    public void updateCategory(Long catId, String name) {
        this.baseMapper.updateCategory(catId, name);
    }

    @Override
    public List<BrandVo> getCateRelationBrands(String catId) {
        List<CategoryBrandRelationEntity> list = this.list(new QueryWrapper<CategoryBrandRelationEntity>().eq(!StringUtils.isEmpty(catId), "catelog_id", catId));
        List<BrandVo> collect = new ArrayList<>();
        if (list != null && list.size() > 0) {
            collect = list.stream().map(item -> {
                System.out.println();
                BrandVo brandVo = new BrandVo();
                BrandEntity brandEntity = brandService.getById(item.getBrandId());
                brandVo.setBrandId(brandEntity.getBrandId());
                brandVo.setBrandName(brandEntity.getName());
                return brandVo;
            }).collect(Collectors.toList());
        }
        return collect;
    }

    @Override
    public List<CatelogVo> getBrandRelationCategory(String brandId) {
        BrandEntity brandEntity = brandService.getById(brandId);
        List<CategoryBrandRelationEntity> list = this.list(new QueryWrapper<CategoryBrandRelationEntity>().eq(!StringUtils.isEmpty(brandId), "brand_id", brandId));
        List<CatelogVo> collect = new ArrayList<>();
        if (list != null && list.size() > 0) {
            collect = list.stream().map(item -> {
                CatelogVo catelogVo = new CatelogVo();
                catelogVo.setBrandName(brandEntity.getName());
                catelogVo.setBrandId(Long.parseLong(brandId));
                CategoryEntity categoryEntity = categoryService.getById(item.getCatelogId());
                catelogVo.setCatelogId(categoryEntity.getCatId());
                catelogVo.setCatelogName(categoryEntity.getName());
                return catelogVo;
            }).collect(Collectors.toList());
        }

        return collect;
    }

    @Override
    public List<AttrGroupCateRelationVo> getCateRelationAttrGroupAndAttrs(String catelogId) {
        List<AttrGroupEntity> l = attrGroupService.list(new QueryWrapper<AttrGroupEntity>().eq(!StringUtils.isEmpty(catelogId), "catelog_id", catelogId));
        List<AttrGroupCateRelationVo> attrGroupCateRelationVos = new ArrayList<AttrGroupCateRelationVo>();
        if (l != null && l.size() > 0) {
            attrGroupCateRelationVos = l.stream().map(item -> {
                AttrGroupCateRelationVo attrGroupCateRelationVo = new AttrGroupCateRelationVo();
                BeanUtils.copyProperties(item, attrGroupCateRelationVo);
                List<AttrAttrgroupRelationEntity> list = attrAttrgroupRelationService.list(new QueryWrapper<AttrAttrgroupRelationEntity>().eq(!StringUtils.isEmpty(item.getAttrGroupId()), "attr_group_id", item.getAttrGroupId()));
                List<AttrEntity> attrEntities = new ArrayList<>();
                if (list != null && list.size() > 0) {
                    attrEntities = list.stream().map(attrAttrgroupRelationEntity -> {
                        AttrEntity attrServiceById = attrService.getById(attrAttrgroupRelationEntity.getAttrId());
                        return attrServiceById;
                    }).collect(Collectors.toList());
                }
                if (attrEntities != null && attrEntities.size() > 0) {
                    attrGroupCateRelationVo.setAttrs(attrEntities);
                }
                return attrGroupCateRelationVo;
            }).collect(Collectors.toList());
        }
        return attrGroupCateRelationVos;
    }

    @Override
    public void removeRelation(CatelogVo catelogVo) {
        this.remove(new QueryWrapper<CategoryBrandRelationEntity>().eq(!StringUtils.isEmpty(catelogVo.getBrandId()),"brand_id",catelogVo.getBrandId()).eq(!StringUtils.isEmpty(catelogVo.getCatelogId()),"catelog_id",catelogVo.getCatelogId()));
    }
}