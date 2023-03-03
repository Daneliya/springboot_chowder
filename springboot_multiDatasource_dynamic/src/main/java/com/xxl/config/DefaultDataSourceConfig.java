//package com.xxl.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.xxl.constant.DbConstants;
//import com.xxl.pojo.DataSourceItem;
//import com.xxl.pojo.properties.DruidYmlDataSource;
//import com.xxl.pojo.properties.DynamicDataSource;
//import com.xxl.pojo.properties.MasterDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 默认数据源
// *
// * @author xxl
// * @date 2022/5/5 23:39
// */
//@Configuration
//public class DefaultDataSourceConfig {
//
////    @Primary
////    @Bean(name = "defaultDataSource")
////    @ConfigurationProperties("spring.datasource.dynamic.datasource.master")
////    public DataSource defaultDataSource() {
////        System.out.println("加载默认数据源---");
////        return DruidDataSourceBuilder.create().build();
////    }
//
//    @Autowired
//    private MasterDataSource masterDataSource;
//
//    @Autowired
//    private DynamicDataSource dynamicDataSource;
//
//    @Autowired
//    private DruidYmlDataSource druidYmlDataSource;
//
//    @Primary
//    @Bean(name = "defaultDataSource")
//    public DataSource defaultDataSource() {
////        DataSourceItem ds0 = DataSourceItem
////                .builder()
////                .key(DbConstants.DEFAULT_DB0)
////                .poolName(DbConstants.DEFAULT_DB0)
////                .url("jdbc:mysql://127.0.0.1:3306/multi_tenant_master?useSSL=false&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai")
////                .username("root")
////                .password("xxl666")
////                .driverClassName("com.mysql.cj.jdbc.Driver")
////                .build();
//
//        System.out.println("masterDataSource " + masterDataSource);
//        System.out.println("dynamicDataSource " + dynamicDataSource);
//        System.out.println("druidYmlDataSource " + druidYmlDataSource);
//
//        DataSourceItem ds0 = DataSourceItem
//                .builder()
//                .key(DbConstants.DEFAULT_MAIN)
//                .poolName(DbConstants.DEFAULT_MAIN)
//                .url(masterDataSource.getUrl())
//                .username(masterDataSource.getUsername())
//                .password(masterDataSource.getPassword())
//                .driverClassName(masterDataSource.getDriverClassName())
//                .build();
//
//        Map<Object, Object> map = new HashMap<>();
//        map.put(ds0.getKey(), buildDataSource(ds0));
//        DynamicDataSourceContext.dataSourceMap.put(ds0.getKey(), buildDataSource(ds0));
//        System.out.println("加载默认数据源---");
//        DynamicDataSourceContext dynamicDataSourceContext = new DynamicDataSourceContext();
//        dynamicDataSourceContext.freshDataSource(map);
//        return dynamicDataSourceContext;
//    }
//
//    /**
//     * 把数据源对象组装成HikariDataSource
//     *
//     * @param dataSourceItem
//     * @return
//     */
//    private static DataSource buildDataSource(DataSourceItem dataSourceItem) {
//        // HikariDataSource dataSource = new HikariDataSource();
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUrl(dataSourceItem.getUrl());
//        dataSource.setUsername(dataSourceItem.getUsername());
//        dataSource.setPassword(dataSourceItem.getPassword());
//        dataSource.setDriverClassName(dataSourceItem.getDriverClassName());
//        return dataSource;
//    }
//
//}