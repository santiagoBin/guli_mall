package com.atguigu.gulimall.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients(basePackages = {"com.atguigu.gulimall.auth.feign"})
@ComponentScan({"com.atguigu"})

public class GulimallAuthApplication {


    public static void main(String[] args) {
        SpringApplication.run(GulimallAuthApplication.class, args);

    }

}
