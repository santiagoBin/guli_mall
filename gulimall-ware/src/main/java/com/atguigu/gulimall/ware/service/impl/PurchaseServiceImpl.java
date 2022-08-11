package com.atguigu.gulimall.ware.service.impl;

import com.atguigu.gulimall.ware.entity.PurchaseDetailEntity;
import com.atguigu.gulimall.ware.entity.WareSkuEntity;
import com.atguigu.gulimall.ware.entity.vo.PurchaseDoneVo;
import com.atguigu.gulimall.ware.entity.vo.PurchaseVo;
import com.atguigu.gulimall.ware.service.PurchaseDetailService;
import com.atguigu.gulimall.ware.service.WareSkuService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.common.utils.Query;

import com.atguigu.gulimall.ware.dao.PurchaseDao;
import com.atguigu.gulimall.ware.entity.PurchaseEntity;
import com.atguigu.gulimall.ware.service.PurchaseService;
import org.springframework.util.StringUtils;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {
    @Autowired
    PurchaseDetailService purchaseDetailService;
    @Autowired
    WareSkuService wareSkuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>().eq(!StringUtils.isEmpty(params.get("status")),"status",params.get("status"))
                .eq(!StringUtils.isEmpty(params.get("key")),"assignee_name",params.get("key"))
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils getUnreceiveList() {
        Page<PurchaseEntity> status = this.page(new Page<>(1, 20), new QueryWrapper<PurchaseEntity>().eq("status", 0));
        return new PageUtils(status);
    }

    @Override
    public void mergePurchase(PurchaseVo purchaseVo) {
        if (purchaseVo.getPurchaseId()!=null){
            PurchaseEntity purchaseEntity = this.getById(purchaseVo.getPurchaseId());
            if (purchaseEntity!=null){
                BigDecimal amount = purchaseEntity.getAmount() == null?BigDecimal.ZERO:purchaseEntity.getAmount();
                List<BigDecimal> priceList = purchaseVo.getItems().stream().map(item -> {
                    PurchaseDetailEntity byId = purchaseDetailService.getById(item);
                    BigDecimal itemPrice = byId.getSkuPrice() == null ? BigDecimal.ZERO : byId.getSkuPrice();
                    PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
                    purchaseDetailEntity.setPurchaseId(purchaseEntity.getId());
                    purchaseDetailEntity.setStatus(1);
                    purchaseDetailService.update(purchaseDetailEntity, new QueryWrapper<PurchaseDetailEntity>().eq("id", item));
                    return itemPrice;
                }).collect(Collectors.toList());
                for (int i = 0; i < priceList.size(); i++) {
                    amount= amount.add(priceList.get(i));
                }
                purchaseEntity.setAmount(amount);
                this.update(purchaseEntity,null);
            }else {
                PurchaseEntity purchaseEntity1 = new PurchaseEntity();
                purchaseEntity1.setPriority(1);
                purchaseEntity1.setStatus(0);
                this.save(purchaseEntity1);
                List<BigDecimal> itmePrice = purchaseVo.getItems().stream().map(item -> {
                    PurchaseDetailEntity byId = purchaseDetailService.getById(item);
                    BigDecimal itemPrice = byId.getSkuPrice() == null ? BigDecimal.ZERO : byId.getSkuPrice();
                    PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
                    purchaseDetailEntity.setPurchaseId(purchaseEntity1.getId());
                    purchaseDetailEntity.setStatus(1);
                    purchaseDetailService.update(purchaseDetailEntity, new QueryWrapper<PurchaseDetailEntity>().eq("id", item));
                    return itemPrice;
                }).collect(Collectors.toList());
                for (int i = 0; i < itmePrice.size(); i++) {
                    purchaseEntity1.setAmount(purchaseEntity1.getAmount().add(itmePrice.get(i)));
                }
                this.update(purchaseEntity1,null);
            }
        } else {
            PurchaseEntity purchaseEntity1 = new PurchaseEntity();
            purchaseEntity1.setPriority(1);
            purchaseEntity1.setStatus(0);
            this.save(purchaseEntity1);
            List<BigDecimal> priceList = purchaseVo.getItems().stream().map(item -> {
                PurchaseDetailEntity byId = purchaseDetailService.getById(item);
                BigDecimal itemPrice = byId.getSkuPrice() == null ? BigDecimal.ZERO : byId.getSkuPrice();
                PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
                purchaseDetailEntity.setStatus(1);
                purchaseDetailEntity.setPurchaseId(purchaseEntity1.getId());
                purchaseDetailService.update(purchaseDetailEntity, new QueryWrapper<PurchaseDetailEntity>().eq("id", item));
                return itemPrice;
            }).collect(Collectors.toList());
            for (int i = 0; i < priceList.size(); i++) {
                purchaseEntity1.setAmount((purchaseEntity1.getAmount()==null?BigDecimal.ZERO:purchaseEntity1.getAmount()).add(priceList.get(i)));
            }
            this.update(purchaseEntity1,null);
        }

    }

    @Override
    public void receivePurchase(ArrayList<Long> purchaseIds) {
        if (purchaseIds!=null&&purchaseIds.size()>0){
            List<PurchaseEntity> purchaseEntities = this.list(new QueryWrapper<PurchaseEntity>().in("id", purchaseIds));
            if (purchaseEntities!=null&&purchaseEntities.size()>0){
                List<PurchaseEntity> collect = purchaseEntities.stream().map(item -> {
                    item.setStatus(2);
                    return item;
                }).collect(Collectors.toList());
                this.updateBatchById(collect);
            }
            List<PurchaseDetailEntity> purchaseDetailEntities = purchaseDetailService.list(new QueryWrapper<PurchaseDetailEntity>().in("purchase_id", purchaseIds));
            if (purchaseDetailEntities!=null&&purchaseDetailEntities.size()>0){
                List<PurchaseDetailEntity> collect = purchaseDetailEntities.stream().map(item -> {
                    item.setStatus(2);
                    return item;
                }).collect(Collectors.toList());
                purchaseDetailService.updateBatchById(collect);
            }
        }

    }

    @Override
    public void purchaseDone(PurchaseDoneVo purchaseDoneVo) {
        if (purchaseDoneVo.getId()!=null&&purchaseDoneVo.getStatus()!=null){
            PurchaseEntity byId = this.getById(purchaseDoneVo.getId());
            if (byId!=null){
                byId.setStatus(purchaseDoneVo.getStatus());
                this.updateById(byId);
            }
            purchaseDoneVo.getItems().stream().map(item->{
                PurchaseDetailEntity purchaseDetailEntity = purchaseDetailService.getById(item.getItemId());
                purchaseDetailEntity.setStatus(item.getStatus());
                purchaseDetailEntity.setReason(item.getReason());
                purchaseDetailService.updateById(purchaseDetailEntity);
                WareSkuEntity wareSku = wareSkuService.getOne(new QueryWrapper<WareSkuEntity>().eq("sku_id", item.getItemId()));
                if (wareSku!=null){
                    wareSku.setStock(wareSku.getStock()+item.getSkuPurchasedNum());
                    wareSkuService.updateById(wareSku);
                }else {
                    WareSkuEntity wareSkuEntity = new WareSkuEntity();
                    wareSkuEntity.setSkuId(purchaseDetailEntity.getSkuId());
                    wareSkuEntity.setWareId(purchaseDetailEntity.getWareId());
                    wareSkuEntity.setStock(item.getSkuPurchasedNum());
                    wareSkuEntity.setSkuName(purchaseDetailEntity.getSkuName());
                    wareSkuEntity.setStockLocked(1);
                    wareSkuService.save(wareSkuEntity);
                }
                return item;
            }).collect(Collectors.toList());
        }
    }
}