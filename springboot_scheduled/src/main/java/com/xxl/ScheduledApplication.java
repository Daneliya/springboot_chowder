package com.xxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: xxl
 * @Date: 2022/07/05 11:55
 */
@SpringBootApplication
@EnableScheduling
public class ScheduledApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduledApplication.class, args);
    }

}