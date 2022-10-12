package com.atguigu.gulimall.seckill.vo;

import java.math.BigDecimal;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-07-09 21:13
 **/

public class SeckillSkuVo {

    private Long id;
    /**
     * 活动id
     */
    private Long promotionId;
    /**
     * 活动场次id
     */
    private Long promotionSessionId;
    /**
     * 商品id
     */
    private Long skuId;
    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;
    /**
     * 秒杀总量
     */
    private Integer seckillCount;
    /**
     * 每人限购数量
     */
    private Integer seckillLimit;
    /**
     * 排序
     */
    private Integer seckillSort;

    public SeckillSkuVo() {
    }

    public Long getId() {
        return this.id;
    }

    public Long getPromotionId() {
        return this.promotionId;
    }

    public Long getPromotionSessionId() {
        return this.promotionSessionId;
    }

    public Long getSkuId() {
        return this.skuId;
    }

    public BigDecimal getSeckillPrice() {
        return this.seckillPrice;
    }

    public Integer getSeckillCount() {
        return this.seckillCount;
    }

    public Integer getSeckillLimit() {
        return this.seckillLimit;
    }

    public Integer getSeckillSort() {
        return this.seckillSort;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public void setPromotionSessionId(Long promotionSessionId) {
        this.promotionSessionId = promotionSessionId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public void setSeckillPrice(BigDecimal seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public void setSeckillCount(Integer seckillCount) {
        this.seckillCount = seckillCount;
    }

    public void setSeckillLimit(Integer seckillLimit) {
        this.seckillLimit = seckillLimit;
    }

    public void setSeckillSort(Integer seckillSort) {
        this.seckillSort = seckillSort;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SeckillSkuVo)) return false;
        final SeckillSkuVo other = (SeckillSkuVo) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$promotionId = this.getPromotionId();
        final Object other$promotionId = other.getPromotionId();
        if (this$promotionId == null ? other$promotionId != null : !this$promotionId.equals(other$promotionId))
            return false;
        final Object this$promotionSessionId = this.getPromotionSessionId();
        final Object other$promotionSessionId = other.getPromotionSessionId();
        if (this$promotionSessionId == null ? other$promotionSessionId != null : !this$promotionSessionId.equals(other$promotionSessionId))
            return false;
        final Object this$skuId = this.getSkuId();
        final Object other$skuId = other.getSkuId();
        if (this$skuId == null ? other$skuId != null : !this$skuId.equals(other$skuId)) return false;
        final Object this$seckillPrice = this.getSeckillPrice();
        final Object other$seckillPrice = other.getSeckillPrice();
        if (this$seckillPrice == null ? other$seckillPrice != null : !this$seckillPrice.equals(other$seckillPrice))
            return false;
        final Object this$seckillCount = this.getSeckillCount();
        final Object other$seckillCount = other.getSeckillCount();
        if (this$seckillCount == null ? other$seckillCount != null : !this$seckillCount.equals(other$seckillCount))
            return false;
        final Object this$seckillLimit = this.getSeckillLimit();
        final Object other$seckillLimit = other.getSeckillLimit();
        if (this$seckillLimit == null ? other$seckillLimit != null : !this$seckillLimit.equals(other$seckillLimit))
            return false;
        final Object this$seckillSort = this.getSeckillSort();
        final Object other$seckillSort = other.getSeckillSort();
        if (this$seckillSort == null ? other$seckillSort != null : !this$seckillSort.equals(other$seckillSort))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SeckillSkuVo;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $promotionId = this.getPromotionId();
        result = result * PRIME + ($promotionId == null ? 43 : $promotionId.hashCode());
        final Object $promotionSessionId = this.getPromotionSessionId();
        result = result * PRIME + ($promotionSessionId == null ? 43 : $promotionSessionId.hashCode());
        final Object $skuId = this.getSkuId();
        result = result * PRIME + ($skuId == null ? 43 : $skuId.hashCode());
        final Object $seckillPrice = this.getSeckillPrice();
        result = result * PRIME + ($seckillPrice == null ? 43 : $seckillPrice.hashCode());
        final Object $seckillCount = this.getSeckillCount();
        result = result * PRIME + ($seckillCount == null ? 43 : $seckillCount.hashCode());
        final Object $seckillLimit = this.getSeckillLimit();
        result = result * PRIME + ($seckillLimit == null ? 43 : $seckillLimit.hashCode());
        final Object $seckillSort = this.getSeckillSort();
        result = result * PRIME + ($seckillSort == null ? 43 : $seckillSort.hashCode());
        return result;
    }

    public String toString() {
        return "SeckillSkuVo(id=" + this.getId() + ", promotionId=" + this.getPromotionId() + ", promotionSessionId=" + this.getPromotionSessionId() + ", skuId=" + this.getSkuId() + ", seckillPrice=" + this.getSeckillPrice() + ", seckillCount=" + this.getSeckillCount() + ", seckillLimit=" + this.getSeckillLimit() + ", seckillSort=" + this.getSeckillSort() + ")";
    }
}
