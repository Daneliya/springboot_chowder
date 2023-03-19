package com.xxl.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/13 13:20
 * @Version: 1.0
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.xxl.dao.slaver", entityManagerFactoryRef = "oracleEntityManagerFactoryRef", transactionManagerRef = "oracleTransactionManagerRef")
public class JpaConfigOracle {

    @Resource
    @Qualifier(value = "oracleDataSource")
    private DataSource oracleDataSource;

    @Bean
    public LocalContainerEntityManagerFactoryBean oracleEntityManagerFactoryRef(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(oracleDataSource)
                .packages("com.xxl.vo")
                .build();
    }

    @Bean
    @SuppressWarnings("ConstantConditions")
    public PlatformTransactionManager oracleTransactionManagerRef(EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean oracleEntityManagerFactoryBean = oracleEntityManagerFactoryRef(builder);
        return new JpaTransactionManager(oracleEntityManagerFactoryBean.getObject());
    }

}