//package com.xxl.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
//import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.persistence.EntityManager;
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
///**
// * @Description: 从数据源配置，和主数据源的配置几乎一样，区别在于没有@Primary注解
// * @Author: xxl
// * @Date: 2023/02/13 11:09
// * @Version: 1.0
// */
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        entityManagerFactoryRef = "entityManagerFactorySlave",//注意名称和主数据源不一样
//        transactionManagerRef = "transactionManagerSlave",//注意名称和主数据源不一样
//        basePackages = {"com.xxl.dao.slaver"}//配置从dao(repository)所在目录
//)
//public class SlaverDataSourceConfig {
//
//    @Autowired
//    @Qualifier("slaverDataSource") //注意指定从数据源
//    private DataSource dataSource;
//
//    @Autowired
//    private JpaProperties jpaProperties;
//
//    @Autowired
//    private HibernateProperties hibernateProperties;
//
//    @Bean("entityManagerSlave")
//    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
//        return Objects.requireNonNull(localContainerEntityManagerFactoryBean(builder).getObject()).createEntityManager();
//    }
//
//    @Bean("entityManagerFactorySlave")
//    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
//        return builder.dataSource(dataSource)
//                .properties(getVendorProperties())
//                //设置实体类所在目录
//                .packages("com.xxl.vo.slaver")
//                //持久化单元名称，当存在多个EntityManagerFactory时，需要制定此名称
//                .persistenceUnit("slavePersistenceUnit")
//                .build();
//    }
//
//    private Map<String, Object> getVendorProperties() {
//        Map<String, String> map = new HashMap<>();
//        map.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
//        jpaProperties.setProperties(map);
//        return hibernateProperties.determineHibernateProperties(
//                jpaProperties.getProperties(),
//                new HibernateSettings()
//        );
//    }
//
//    @Bean("transactionManagerSlave")
//    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
//        return new JpaTransactionManager(Objects.requireNonNull(localContainerEntityManagerFactoryBean(builder).getObject()));
//    }
//}