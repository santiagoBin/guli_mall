package com.atguigu.gulimall.coupon.service.impl;

import com.atguigu.gulimall.common.to.seckill.SeckillSessionTo;
import com.atguigu.gulimall.common.to.seckill.SeckillSkuRelationTo;
import com.atguigu.gulimall.coupon.entity.SeckillSkuRelationEntity;
import com.atguigu.gulimall.coupon.service.SeckillSkuRelationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gulimall.common.utils.PageUtils;
import com.atguigu.gulimall.common.utils.Query;

import com.atguigu.gulimall.coupon.dao.SeckillSessionDao;
import com.atguigu.gulimall.coupon.entity.SeckillSessionEntity;
import com.atguigu.gulimall.coupon.service.SeckillSessionService;


@Service("seckillSessionService")
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionDao, SeckillSessionEntity> implements SeckillSessionService {
    @Autowired
    SeckillSkuRelationService seckillSkuRelationService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SeckillSessionEntity> page = this.page(
                new Query<SeckillSessionEntity>().getPage(params),
                new QueryWrapper<SeckillSessionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<SeckillSessionTo> getLatest3DaySession() {
        List<SeckillSessionEntity> list = this.baseMapper.selectList(
                new QueryWrapper<SeckillSessionEntity>().between("start_time", startTime(), endTime()).orderByAsc("start_time"));
        if (list!=null&&list.size()>0){
            List<SeckillSessionTo> collect = list.stream().map(session -> {
                SeckillSessionTo seckillSessionTo = new SeckillSessionTo();
                Long id = session.getId();
                List<SeckillSkuRelationEntity> relationEntities = seckillSkuRelationService.list(
                        new QueryWrapper<SeckillSkuRelationEntity>().eq("promotion_session_id", id));
                if (relationEntities != null && relationEntities.size() > 0) {
                    List<SeckillSkuRelationTo> seckillSkuRelationTos = relationEntities.stream().map(item -> {
                        SeckillSkuRelationTo seckillSkuRelationTo = new SeckillSkuRelationTo();
                        BeanUtils.copyProperties(item, seckillSkuRelationTo);
                        return seckillSkuRelationTo;
                    }).collect(Collectors.toList());
                    session.setRelationSkus(seckillSkuRelationTos);
                }
                BeanUtils.copyProperties(session, seckillSessionTo);
                return seckillSessionTo;
            }).collect(Collectors.toList());
            return collect;
        }
        return null;
    }

    private String startTime() {
        LocalDate now = LocalDate.now();
        LocalTime min = LocalTime.MIN;
        LocalDateTime start = LocalDateTime.of(now, min);
        String startFormat = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return startFormat;
    }

    private String endTime() {
        LocalDate now = LocalDate.now();
        LocalDate pl = now.plusDays(2);
        LocalTime min = LocalTime.MAX;
        LocalDateTime end = LocalDateTime.of(pl, min);
        String endFormat = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return endFormat;
    }

}