package com.xxl.elasticsearch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description: 启动类
 * @Author: xxl
 * @Date: 2023/03/18 17:45
 * @Version: 1.0
 */
@MapperScan("com.xxl.elasticsearch.mapper")
@SpringBootApplication
public class ElasticsearchJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchJavaApplication.class, args);
    }
}
