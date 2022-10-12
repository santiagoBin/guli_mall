package com.atguigu.gulimall.seckill.schedule;

import com.atguigu.gulimall.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SeckillSkuSchedule {

    @Autowired
    SeckillService seckillService;
    @Autowired
    RedissonClient redissonClient;

    private final String upload_lock = "seckill:upload:lock";

    @Scheduled(cron = "0 0/5 0 ? * * ")
    public void uploadSeckillSkuScheduleLatest3Days(){
        log.info("上架秒杀商品...");
        RLock lock = redissonClient.getLock(upload_lock);
        try{
            lock.lock(10, TimeUnit.SECONDS);
            seckillService.uploadSeckillSku();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}
