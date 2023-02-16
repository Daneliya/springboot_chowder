package com.xxl.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/13 13:21
 * @Version: 1.0
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.xxl.dao.master", entityManagerFactoryRef = "mysqlEntityManagerFactoryRef", transactionManagerRef = "mysqlTransactionManagerRef")
public class JpaConfigMySql {

    @Resource
    @Qualifier(value = "mysqlDataSource")
    private DataSource mysqlDataSource;

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactoryRef(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(mysqlDataSource)
                .packages("com.xxl.vo")
                .build();
    }

    @Bean
    @SuppressWarnings("ConstantConditions")
    public PlatformTransactionManager mysqlTransactionManagerRef(EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactoryBean = mysqlEntityManagerFactoryRef(builder);
        return new JpaTransactionManager(mysqlEntityManagerFactoryBean.getObject());
    }

}