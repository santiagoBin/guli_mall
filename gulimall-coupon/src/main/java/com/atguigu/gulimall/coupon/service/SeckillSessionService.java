package com.atguigu.gulimall.coupon.service;

import com.atguigu.gulimall.common.to.seckill.SeckillSessionTo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.coupon.entity.SeckillSessionEntity;

import java.util.List;
import java.util.Map;

/**
 * 秒杀活动场次
 *
 * @author santiago
 * @email 2457039825@qq.com
 * @date 2022-07-18 22:36:17
 */
public interface SeckillSessionService extends IService<SeckillSessionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SeckillSessionTo> getLatest3DaySession();
}

