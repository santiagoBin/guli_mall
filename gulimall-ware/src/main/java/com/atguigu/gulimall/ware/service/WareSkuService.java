package com.atguigu.gulimall.ware.service;

import com.atguigu.gulimall.common.to.WareHasStockTo;
import com.atguigu.gulimall.common.to.mq.StockLockedTo;
import com.atguigu.gulimall.common.to.order.OrderTo;
import com.atguigu.gulimall.ware.vo.WareSkuLockVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.ware.entity.WareSkuEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author santiago
 * @email 2457039825@qq.com
 * @date 2022-07-18 23:01:05
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<WareHasStockTo> hasStock(List<Long> skuIds);

    boolean orderLockStock(WareSkuLockVo wareSkuLockVo);

    void unlockStock(StockLockedTo to);

    void unlockStock(OrderTo orderTo);
}

