package com.xxl.x_file_storage.config;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Date;

/**
 * 项目启动初始化数据
 *
 * @Author: xxl
 * @Date: 2024/5/3 下午6:02
 */
@Configuration
@Slf4j
public class DataInitializationConfig {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    public void init() {
        // 项目启动初始化基本数据
        log.info("数据初始化开始: " + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        // 通过直接读取sql文件执行
        ClassPathResource resources = new ClassPathResource("sql/client_api_init.sql");
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScripts(resources);
        resourceDatabasePopulator.execute(dataSource);
        log.info("数据初始化结束: " + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }
}