package com.xxl.minio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Minio 启动类
 *
 * @Author: xxl
 * @Date: 2022/09/14 16:56
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MinioApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinioApplication.class, args);
    }
}
