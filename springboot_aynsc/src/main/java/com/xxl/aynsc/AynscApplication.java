package com.xxl.aynsc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动类
 *
 * @Author: xxl
 * @Date: 2024/11/22 11:20
 */
@EnableAsync
@SpringBootApplication
public class AynscApplication {

    public static void main(String[] args) {
        SpringApplication.run(AynscApplication.class, args);
    }

}
