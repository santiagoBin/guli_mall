package com.atguigu.gulimall.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson2.util.RyuDouble;
import com.atguigu.gulimall.common.to.mq.SeckillOrderTo;
import com.atguigu.gulimall.common.to.seckill.SeckillSessionTo;
import com.atguigu.gulimall.common.utils.R;
import com.atguigu.gulimall.common.vo.MemberRespVo;
import com.atguigu.gulimall.seckill.feign.CouponFeignClient;
import com.atguigu.gulimall.seckill.feign.ProductFeignClient;
import com.atguigu.gulimall.seckill.interceptor.LoginUserInterceptor;
import com.atguigu.gulimall.seckill.service.SeckillService;
import com.atguigu.gulimall.seckill.to.SeckillSkuRedisTo;
import com.atguigu.gulimall.seckill.vo.SeckillSessionWithSkusVo;
import com.atguigu.gulimall.seckill.vo.SkuInfoVo;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {
    @Autowired
    CouponFeignClient couponFeignClient;
    @Autowired
    ProductFeignClient productFeignClient;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    RabbitTemplate rabbitTemplate;


    private final String SESSION__CACHE_PREFIX = "seckill:sessions:";

    private final String SECKILL_CHARE_PREFIX = "seckill:skus";

    private final String SKU_STOCK_SEMAPHORE = "seckill:stock:";    //+商品随机码
    @Override
    public void uploadSeckillSku() {
        R r = couponFeignClient.getLatest3DaySession();
        if (r.getCode() == 0){
            List<SeckillSessionTo> sessionData = r.getData(new TypeReference<List<SeckillSessionTo>>() {
            });
            if (sessionData!=null&&sessionData.size()>0){
                saveSessionData(sessionData);
                saveSessionSkuInfo(sessionData);
            }
        }
    }

    @Override
    public List<SeckillSkuRedisTo> getCurrentSeckillSkus() {
        long currentTimeMillis = System.currentTimeMillis();
        Set<String> keys = redisTemplate.keys(SESSION__CACHE_PREFIX + "*");
        List<SeckillSkuRedisTo> collect = new ArrayList<>();
        for (String key:keys){
            String replace = key.replace(SESSION__CACHE_PREFIX, "");
            String[] split = replace.split("-");
            long startTime = Long.parseLong(split[0]);
            long endTime = Long.parseLong(split[1]);
            if (currentTimeMillis>=startTime&&currentTimeMillis<=endTime){
                List<String> range = redisTemplate.opsForList().range(key, 0, -1);
                BoundHashOperations<String, String, String> operations = redisTemplate.boundHashOps(SECKILL_CHARE_PREFIX);
                assert range != null;
                List<String> list = operations.multiGet(range);
                if (list!=null&&list.size()>=0){
                    List<SeckillSkuRedisTo> collect1 = list.stream().map(item -> {
                        String items = (String) item;
                        SeckillSkuRedisTo seckillSkuRedisTo = JSON.parseObject(items, SeckillSkuRedisTo.class);
                        return seckillSkuRedisTo;
                    }).collect(Collectors.toList());
                    collect.addAll(collect1);
                }
            }
        }
        return collect;
    }

    @Override
    public SeckillSkuRedisTo getSkuSeckillInfo(Long skuId) {
        BoundHashOperations<String, String, String> operations = redisTemplate.boundHashOps(SECKILL_CHARE_PREFIX);
        Set<String> keys = operations.keys();
        if (keys!=null&&keys.size()>0){
            String reg = "\\d-"+skuId;
            for (String key:keys){
                if (Pattern.matches(reg,key)){
                    String redisValue = operations.get(key);
                    SeckillSkuRedisTo seckillSkuRedisTo = JSON.parseObject(redisValue, SeckillSkuRedisTo.class);
                    Long currentTimeMillis = System.currentTimeMillis();
                    Long startTime = seckillSkuRedisTo.getStartTime();
                    Long endTime = seckillSkuRedisTo.getEndTime();
                    if (currentTimeMillis>=startTime&&currentTimeMillis<=endTime){
                        return seckillSkuRedisTo;
                    }
                    seckillSkuRedisTo.setRandomCode(null);
                    return seckillSkuRedisTo;
                }
            }
        }
        return null;
    }

    @Override
    public String kill(String killId, String key, Integer num) throws InterruptedException {
        long s1 = System.currentTimeMillis();
        MemberRespVo user = LoginUserInterceptor.loginUser.get();
        BoundHashOperations<String, String , String> operations = redisTemplate.boundHashOps(SECKILL_CHARE_PREFIX);
        String skuInfoValue = operations.get(killId);
        if (StringUtils.isEmpty(skuInfoValue)){
            return null;
        }
        SeckillSkuRedisTo redisTo = JSON.parseObject(skuInfoValue, SeckillSkuRedisTo.class);
        Long startTime = redisTo.getStartTime();
        Long endTime = redisTo.getEndTime();
        Long currentTime = System.currentTimeMillis();
        if (currentTime>=startTime&&currentTime<=endTime){
            String randomCode = redisTo.getRandomCode();
            String skuId = redisTo.getPromotionSessionId() + "-" + redisTo.getSkuId();
            if (randomCode.equals(key)&&killId.equals(skuId)){
                Integer seckillLimit = redisTo.getSeckillLimit();
                String seckillCount = redisTemplate.opsForValue().get(SKU_STOCK_SEMAPHORE + randomCode);
                Integer count = Integer.valueOf(seckillCount);
                if (num>0&&count>0&&num<=seckillLimit&&count>num){
                    String redisKey = user.getId() + "-" + skuId;
                    Long ttl = endTime - currentTime;
                    Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(redisKey, num.toString(), ttl, TimeUnit.MILLISECONDS);
                    if (aBoolean){
                        RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + randomCode);
                        boolean b = semaphore.tryAcquire(num, 100, TimeUnit.MILLISECONDS);
                        if (b){
                            String timeId = IdWorker.getTimeId();
                            SeckillOrderTo seckillOrderTo = new SeckillOrderTo();
                            seckillOrderTo.setOrderSn(timeId);
                            seckillOrderTo.setMemberId(Long.parseLong(user.getId()));
                            seckillOrderTo.setNum(num);
                            seckillOrderTo.setPromotionSessionId(redisTo.getPromotionSessionId());
                            seckillOrderTo.setSkuId(redisTo.getSkuId());
                            seckillOrderTo.setSeckillPrice(redisTo.getSeckillPrice());
                            seckillOrderTo.setRandomCode(randomCode);
                            rabbitTemplate.convertAndSend("order-event-exchange","order.seckill.order",seckillOrderTo);
                            long s2 = System.currentTimeMillis();
                            log.info("耗时..."+(s2-s1));
                            return timeId;
                        }
                    }
                }
            }
        }
        long s3 = System.currentTimeMillis();
        log.info("耗时..."+(s3-s1));
        return null;

    }

    private void saveSessionSkuInfo(List<SeckillSessionTo> sessionData) {
        if (sessionData!=null&&sessionData.size()>0){
            sessionData.stream().forEach(session->{
                BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps(SECKILL_CHARE_PREFIX);
                session.getRelationSkus().stream().forEach(seckillSkuVo -> {
                    String token = UUID.randomUUID().toString().replace("-", "");
                    String redisKey = seckillSkuVo.getPromotionSessionId().toString() + "-" + seckillSkuVo.getSkuId().toString();
                    if (!operations.hasKey(redisKey)){
                        SeckillSkuRedisTo seckillSkuRedisTo = new SeckillSkuRedisTo();
                        Long skuId = seckillSkuVo.getSkuId();
                        R r = productFeignClient.info(skuId);
                        log.info(r.toString());
                        if (r.getCode() == 0){
                            String skuInfo = JSON.toJSONString(r.get("skuInfo"));
                            SkuInfoVo skuInfoVo = JSON.parseObject(skuInfo, SkuInfoVo.class);
                            log.info(skuInfoVo.toString());
                            seckillSkuRedisTo.setSkuInfo(skuInfoVo);
                        }
                        BeanUtils.copyProperties(seckillSkuVo,seckillSkuRedisTo);
                        seckillSkuRedisTo.setStartTime(session.getStartTime().getTime());
                        seckillSkuRedisTo.setEndTime(session.getEndTime().getTime());
                        seckillSkuRedisTo.setRandomCode(token);
                        String seckillValue = JSON.toJSONString(seckillSkuRedisTo);
                        operations.put(seckillSkuVo.getPromotionSessionId().toString()+"-"+seckillSkuVo.getSkuId().toString(),seckillValue);
                        RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + token);
                        semaphore.trySetPermits(seckillSkuVo.getSeckillCount());
                    }
                });
            });
        }
    }

    private void saveSessionData(List<SeckillSessionTo> sessionData) {
        if (sessionData!=null&&sessionData.size()>0){
            sessionData.stream().forEach(session->{
                long startTime = session.getStartTime().getTime();
                long endTime = session.getEndTime().getTime();
                String key = SESSION__CACHE_PREFIX + startTime + "-" + endTime;
                Boolean aBoolean = redisTemplate.hasKey(key);
                if (!aBoolean){
                    List<String> skuIds = session.getRelationSkus().stream().map(item -> item.getPromotionSessionId() + "-" + item.getSkuId().toString()
                    ).collect(Collectors.toList());
                    redisTemplate.opsForList().leftPushAll(key,skuIds);
                }
            });
        }
    }


}
