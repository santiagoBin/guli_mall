package com.atguigu.gulimall.product.service;

import com.atguigu.gulimall.product.entity.vo.AttrGroupRelationVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.product.entity.AttrGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author santiago
 * @email 2457039825@qq.com
 * @date 2022-07-18 21:38:20
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(String cateId,Map<String, Object> params);

    void removeRelations(AttrGroupRelationVo[] relationVos);

    void removeAttrGroupAndRelation(List<Long> asList);

    PageUtils queryNoRelationAttr(String attrgroupId, Map<String, Object> params);

    void saveAttrRelationBatch(List<AttrGroupRelationVo> relationVos);
}

