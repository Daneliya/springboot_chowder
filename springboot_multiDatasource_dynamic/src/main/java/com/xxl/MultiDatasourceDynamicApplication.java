package com.xxl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 启动类
 */
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.xxl.mapper")
@SpringBootApplication
public class MultiDatasourceDynamicApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiDatasourceDynamicApplication.class, args);
    }

}
