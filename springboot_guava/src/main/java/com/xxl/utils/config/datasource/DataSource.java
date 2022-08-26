package com.xxl.utils.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author xxl
 * @date 2022/8/26 21:26
 */
@Configuration
public class DataSource {
    @Value("${mysql.app.url}")
    private String url;
    @Value("${mysql.app.userName}")
    private String userName;
    @Value("${mysql.app.password}")
    private String password;
    @Value("${mysql.app.driverClassName}")
    private String driverClassName;

    @Bean(name = "appdataSource")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

//    @Bean(name = "appJdbcTemplate")
//    public JdbcTemplate myJdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
}