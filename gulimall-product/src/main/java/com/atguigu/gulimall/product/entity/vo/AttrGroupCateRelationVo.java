package com.atguigu.gulimall.product.entity.vo;

import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.entity.AttrGroupEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class AttrGroupCateRelationVo extends AttrGroupEntity {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<AttrEntity> attrs;
}
