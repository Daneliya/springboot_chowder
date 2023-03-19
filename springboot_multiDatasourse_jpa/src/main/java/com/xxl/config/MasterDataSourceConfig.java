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
//import org.springframework.context.annotation.Primary;
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
// * @Description: 主数据源配置，和从(其他)数据源的区别在于多了个@Primary注解，意思是主要的，多数据源必须设置一个主数据源
// * @Author: xxl
// * @Date: 2023/02/13 11:05
// * @Version: 1.0
// */
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        entityManagerFactoryRef = "entityManagerFactoryMaster",//配置连接工厂 entityManagerFactory
//        transactionManagerRef = "transactionManagerMaster", //配置事物管理器  transactionManager
//        basePackages = {"com.xxl.dao.master"} //配置主dao(repository)所在目录
//)
//public class MasterDataSourceConfig {
//
//    @Autowired
//    @Qualifier("masterDataSource")  //指定这是主数据源，为了和从(其他)数据源区别开，因为@Autowired不能导入名称相同的是bean
//    private DataSource dataSourceMaster;
//
//    @Autowired
//    private JpaProperties jpaProperties;
//
//    @Autowired
//    private HibernateProperties hibernateProperties;
//
//    /**
//     * 多了个@Primary
//     */
//    @Primary
//    @Bean("entityManagerMaster")
//    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
//        return Objects.requireNonNull(entityManagerFactoryBean(builder).getObject()).createEntityManager();
//    }
//
//    /**
//     * 多了个@Primary
//     */
//    @Primary
//    @Bean("entityManagerFactoryMaster")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
//        return builder.dataSource(dataSourceMaster)
//                .properties(getVendorProperties())
//                //设置实体类所在目录
//                .packages("com.xxl.vo.master")
//                //持久化单元名称，当存在多个EntityManagerFactory时，需要制定此名称
//                .persistenceUnit("masterPersistenceUnit")
//                .build();
//
//    }
//
//    private Map<String, Object> getVendorProperties() {
//        Map<String, String> map = new HashMap<>();
//        map.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
//        jpaProperties.setProperties(map);
//        return hibernateProperties.determineHibernateProperties(
//                jpaProperties.getProperties(),
//                new HibernateSettings()
//        );
//    }
//
//    /**
//     * 多了个@Primary
//     */
//    @Primary
//    @Bean("transactionManagerMaster")
//    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
//        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactoryBean(builder).getObject()));
//    }
//}