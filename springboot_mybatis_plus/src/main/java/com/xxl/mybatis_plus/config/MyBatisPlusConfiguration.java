package com.xxl.mybatis_plus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * MyBatisPlus配置
 *
 * @author xxl
 * @date 2023/4/19 0:50
 */
@Configuration
public class MyBatisPlusConfiguration {

    /**
     * 分页插件，自动识别数据库类型
     * 多租户，请参考官网【插件扩展】
     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        // 开启 PageHelper 的支持
//        paginationInterceptor.setDialectType("mysql");
//        paginationInterceptor.setLocalPage(true);
//        return paginationInterceptor;
//    }

    /**
     * MyBatiesPlus sql执行性能分析（旧版本）
     *
     * @return
     */
//    @Bean
//    public PerformanceInterceptor performanceInterceptor() {
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//        //格式化sql语句
//        Properties properties = new Properties();
//        properties.setProperty("format", "false");
//        performanceInterceptor.setProperties(properties);
//        return performanceInterceptor;
//    }
}