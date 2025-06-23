package com.xxl.graylog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @Author: xxl
 * @Date: 2025/6/23 13:42
 */
@SpringBootApplication
@MapperScan("com.xxl.graylog.mapper")
public class GraylogApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraylogApplication.class, args);
    }

}