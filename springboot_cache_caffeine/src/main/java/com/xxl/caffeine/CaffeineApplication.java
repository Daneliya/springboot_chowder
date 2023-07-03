package com.xxl.caffeine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author xxl
 * @date 2022/12/6 0:38
 */
@EnableCaching
@SpringBootApplication
public class CaffeineApplication {
    public static void main(String[] args) {
        SpringApplication.run(CaffeineApplication.class, args);
    }
}
