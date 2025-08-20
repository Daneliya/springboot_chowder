package com.xxl.retry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

/**
 * 启动类
 *
 * @author xxl
 * @date 2022/12/26 22:43
 */
@EnableRetry
@SpringBootApplication
public class ReplyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReplyApplication.class, args);
    }

}
