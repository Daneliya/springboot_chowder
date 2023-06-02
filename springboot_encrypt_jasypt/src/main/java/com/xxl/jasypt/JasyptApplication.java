package com.xxl.jasypt;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 */
@MapperScan(value = "com.xxl.jasypt.mapper*", annotationClass = Mapper.class)
@SpringBootApplication
public class JasyptApplication {

    public static void main(String[] args) {
        SpringApplication.run(JasyptApplication.class, args);
    }

}
