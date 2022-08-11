package com.atguigu.gulimall.ware.entity.vo;

import lombok.Data;

import java.util.ArrayList;
@Data
public class PurchaseDoneVo {
    private Long id;
    private Integer status;
    private ArrayList<PurchaseFailItem> items;
}
