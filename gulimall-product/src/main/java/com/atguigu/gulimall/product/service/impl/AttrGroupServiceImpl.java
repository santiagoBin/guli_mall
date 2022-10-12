package com.atguigu.gulimall.product.service.impl;

import com.atguigu.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.entity.vo.AttrGroupRelationVo;
import com.atguigu.gulimall.product.entity.vo.SpuItemAttrGroupVo;
import com.atguigu.gulimall.product.service.AttrAttrgroupRelationService;
import com.atguigu.gulimall.product.service.AttrService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.common.utils.Query;

import com.atguigu.gulimall.product.dao.AttrGroupDao;
import com.atguigu.gulimall.product.entity.AttrGroupEntity;
import com.atguigu.gulimall.product.service.AttrGroupService;
import org.springframework.util.StringUtils;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {
    @Autowired
    AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Autowired
    AttrService attrService;
    private PageUtils pageUtils;

    @Override
    public PageUtils queryPage(String cateId, Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>().eq(!cateId.equals("0"), "catelog_id", cateId).eq(!StringUtils.isEmpty(params.get("key")), "attr_group_id", params.get("key")).or().like(!StringUtils.isEmpty(params.get("key")), "attr_group_name", params.get("key"))
        );
        return new PageUtils(page);

    }

    @Override
    public void removeRelations(AttrGroupRelationVo[] relationVos) {
        List<AttrAttrgroupRelationEntity> collect = Arrays.asList(relationVos).stream().map(relationVo -> {
            AttrAttrgroupRelationEntity attrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(relationVo, attrgroupRelationEntity);
            return attrgroupRelationEntity;
        }).collect(Collectors.toList());
        baseMapper.deleteRelations(collect);
    }

    @Override
    public void removeAttrGroupAndRelation(List<Long> asList) {
        asList.stream().map(attrGroupId -> {
            this.remove(new QueryWrapper<AttrGroupEntity>().eq("attr_group_id", attrGroupId));
            attrAttrgroupRelationService.remove(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrGroupId));
            return null;
        }).close();
    }

    @Override
    public PageUtils queryNoRelationAttr(String attrgroupId, Map<String, Object> params) {
        AttrGroupEntity attrGroupEntity = this.getById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();
        List<AttrGroupEntity> otherAttrGroupList = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        List<Long> usedAttrIds = new ArrayList<>();
        if (otherAttrGroupList.size()>0){
            otherAttrGroupList.stream().map((item) -> {
                List<AttrAttrgroupRelationEntity> list = attrAttrgroupRelationService.list(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", item.getAttrGroupId()));
                list.forEach(attrAttrgroupRelationEntity -> {
                    usedAttrIds.add(attrAttrgroupRelationEntity.getAttrId());
                });

                return null;
            }).collect(Collectors.toList());
        }
        IPage<AttrEntity> page = attrService.page(new Query<AttrEntity>().getPage(params), new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId).notIn(usedAttrIds.size() > 0, "attr_id", usedAttrIds).eq(!StringUtils.isEmpty(params.get("key")), "attr_id", params.get("key")).or().like(!StringUtils.isEmpty(params.get("key")), "attr_name", params.get("key")));
        return new PageUtils(page);
    }

    @Override
    public void saveAttrRelationBatch(List<AttrGroupRelationVo> relationVos) {
        relationVos.stream().peek(item->{
            AttrAttrgroupRelationEntity attrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item,attrgroupRelationEntity);
            attrAttrgroupRelationService.save(attrgroupRelationEntity);
        }).collect(Collectors.toList());
    }

    @Override
    public List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catelogId) {
        List<SpuItemAttrGroupVo> list =  baseMapper.getAttrGroupWithAttrsBySpuId(spuId,catelogId);
        return list;
    }
}