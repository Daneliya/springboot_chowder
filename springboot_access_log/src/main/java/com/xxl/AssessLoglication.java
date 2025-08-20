package com.xxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 启动类
 *
 * @author xxl
 * @date 2022/5/8 18:54
 */
@EnableAspectJAutoProxy
@SpringBootApplication
public class AssessLoglication {

    public static void main(String[] args) {
        SpringApplication.run(AssessLoglication.class, args);
    }

}
