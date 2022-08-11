package com.atguigu.gulimall.common.to;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class SkuInfoTo {
    private Long skuId;
    private BigDecimal price;
    private String skuName;
}
