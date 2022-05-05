package com.xxl.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 默认数据源
 *
 * @author xxl
 * @date 2022/5/5 23:39
 */
@Configuration
public class DefaultDataSourceConfig {

    @Primary
    @Bean(name = "defaultDataSource")
    @ConfigurationProperties("spring.datasouce")
    public DataSource defaultDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

}
