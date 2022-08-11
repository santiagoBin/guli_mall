package com.atguigu.gulimall.ware.entity.vo;

import lombok.Data;

@Data
public class PurchaseFailItem {
    private Long itemId;
    private Integer status;
    private String reason;
    private Integer skuPurchasedNum;
}
