package com.xxl.resilience4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动类
 *
 * @Author: xxl
 * @Date: 2025/3/20 13:21
 */
@SpringBootApplication
@EnableCaching //开启缓存
public class StartResilience4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartResilience4jApplication.class, args);
    }
}