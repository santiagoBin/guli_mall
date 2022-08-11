package com.atguigu.gulimall.ware.service;

import com.atguigu.gulimall.ware.entity.vo.PurchaseDoneVo;
import com.atguigu.gulimall.ware.entity.vo.PurchaseVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.ware.entity.PurchaseEntity;

import java.util.ArrayList;
import java.util.Map;

/**
 * 采购信息
 *
 * @author santiago
 * @email 2457039825@qq.com
 * @date 2022-07-18 23:01:05
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils getUnreceiveList();

    void mergePurchase(PurchaseVo purchaseVo);

    void receivePurchase(ArrayList<Long> purchaseIds);

    void purchaseDone(PurchaseDoneVo purchaseDoneVo);
}

