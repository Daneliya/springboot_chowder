//package com.xxl.config2;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.xxl.config.DynamicDataSourceContext;
//import com.xxl.pojo.DataSourceItem;
//import com.xxl.pojo.properties.DruidYmlDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
///***
// * @Author 徐庶   QQ:1092002729
// * @Slogan 致敬大师，致敬未来的你
// */
//@Component
//public class DynamicDataSourceConfig {
//
//    @Autowired
//    private DruidYmlDataSource druidYmlDataSource;
//
//    @Bean
//    @Primary
//    public DynamicDataSourceContext dataSource() {
//        System.out.println("加载所有数据源---");
//        Map<Object, Object> targetDataSource = getDataSource();
//        // 把DynamicDataSourceContext纳入容器管理
//        DynamicDataSourceContext dynamicDataSourceContext = new DynamicDataSourceContext();
//        dynamicDataSourceContext.freshDataSource(targetDataSource);
//        return dynamicDataSourceContext;
//    }
//
//    /**
//     * 构建初始化数据源 TODO 生成中去其他地方获取初始化数据源(例如:表里面获取)
//     *
//     * @return
//     */
//    public Map<Object, Object> getDataSource() {
//        Map<Object, Object> map = new HashMap<>();
//        for (String name : druidYmlDataSource.getDatasource().keySet()) {
//            System.out.println("加载的配置中数据源：" + name);
//            DruidYmlDataSource.Properties properties = druidYmlDataSource.getDatasource().get(name);
//            DataSourceItem ds = DataSourceItem
//                    .builder()
//                    .key(name)
//                    .poolName(name)
//                    .url(properties.getUrl())
//                    .username(properties.getUsername())
//                    .password(properties.getPassword())
//                    .driverClassName(properties.getDriverClassName())
//                    .build();
//            map.put(ds.getKey(), buildDataSource(ds));
////            DynamicDataSource.name.set(ds.getKey(), buildDataSource(ds));
//        }
//        return map;
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
////
////    @Bean
////    @ConfigurationProperties(prefix = "spring.datasource.datasource1")
////    public DataSource dataSource1() {
////        // 底层会自动拿到spring.datasource中的配置， 创建一个DruidDataSource
////        return DruidDataSourceBuilder.create().build();
////    }
////
////    @Bean
////    @ConfigurationProperties(prefix = "spring.datasource.datasource2")
////    public DataSource dataSource2() {
////        // 底层会自动拿到spring.datasource中的配置， 创建一个DruidDataSource
////        return DruidDataSourceBuilder.create().build();
////    }
//
//    /* @Bean
//    public Interceptor dynamicDataSourcePlugin(){
//        return new DynamicDataSourcePlugin();
//    }
//*/
//
////    @Bean
////    public DataSourceTransactionManager transactionManager1(DynamicDataSource dataSource) {
////        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
////        dataSourceTransactionManager.setDataSource(dataSource);
////        return dataSourceTransactionManager;
////    }
////
////    @Bean
////    public DataSourceTransactionManager transactionManager2(DynamicDataSource dataSource) {
////        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
////        dataSourceTransactionManager.setDataSource(dataSource);
////        return dataSourceTransactionManager;
////    }
//}
