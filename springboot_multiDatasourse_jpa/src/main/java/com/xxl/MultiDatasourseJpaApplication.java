package com.xxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 启动类
 */
//@EnableJpaRepositories
@SpringBootApplication
public class MultiDatasourseJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiDatasourseJpaApplication.class, args);
    }

}
