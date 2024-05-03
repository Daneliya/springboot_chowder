package com.xxl.x_file_storage;

import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 */
@EnableFileStorage
@SpringBootApplication
public class SpringbootXFileStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootXFileStorageApplication.class, args);
    }

}
