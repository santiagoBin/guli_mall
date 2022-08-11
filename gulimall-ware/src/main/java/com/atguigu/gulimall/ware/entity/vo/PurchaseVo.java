package com.atguigu.gulimall.ware.entity.vo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class PurchaseVo {
    Long PurchaseId;
    ArrayList<Long> items;
}
