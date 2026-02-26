package com.xxl.easyes;

import cn.easyes.starter.register.EsMapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author xxl
 * @date 2026/2/24 08:47
 */
@EsMapperScan("com.xxl.easyes.mapper")
@SpringBootApplication
public class EasyEsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyEsApplication.class, args);
    }
}
