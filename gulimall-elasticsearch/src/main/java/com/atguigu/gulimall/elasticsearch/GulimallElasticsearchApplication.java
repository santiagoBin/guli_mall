package com.atguigu.gulimall.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients(basePackages = {"com.atguigu.gulimall.elasticsearch.feign"})
@ComponentScan("com.atguigu")
public class GulimallElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallElasticsearchApplication.class, args);
    }

}
