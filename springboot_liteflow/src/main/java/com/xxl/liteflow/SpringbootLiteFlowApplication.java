package com.xxl.liteflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 *
 * @author xxl
 * @date 2024/5/6 22:48
 */
@ComponentScan({"com.xxl.liteflow"})
@SpringBootApplication
public class SpringbootLiteFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootLiteFlowApplication.class, args);
    }
}
