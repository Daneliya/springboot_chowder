//package com.xxl.config2;
//
//import com.xxl.constant.DbConstants;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//
//import java.util.Map;
//
///***
// * @Author 徐庶   QQ:1092002729
// * @Slogan 致敬大师，致敬未来的你
// */
////@Component
////@Primary   // 将该Bean设置为主要注入Bean
//public class DynamicDataSourceContext extends AbstractRoutingDataSource {
//
//
//    // 当前使用的数据源标识
//    public static ThreadLocal<String> name = new ThreadLocal<>();
//
////    // 写
////    @Autowired
////    DataSource dataSource1;
////    // 读
////    @Autowired
////    DataSource dataSource2;
//
//
//    // 返回当前数据源标识
//    @Override
//    protected Object determineCurrentLookupKey() {
//        return name.get();
//
//    }
//
//
////    @Override
////    public void afterPropertiesSet() {
////
////        // 为targetDataSources初始化所有数据源
////        Map<Object, Object> targetDataSources = new HashMap<>();
////        targetDataSources.put("W", dataSource1);
////        targetDataSources.put("R", dataSource2);
////
////        super.setTargetDataSources(targetDataSources);
////
////        // 为defaultTargetDataSource 设置默认的数据源
////        super.setDefaultTargetDataSource(dataSource1);
////
////        super.afterPropertiesSet();
////    }
//
//    /**
//     * 设置默认数据源、全部数据源，及刷新
//     */
//    public void freshDataSource(Map<Object, Object> targetDataSources) {
//        //默认数据源
//        super.setDefaultTargetDataSource(targetDataSources.get(DbConstants.DEFAULT_MAIN));
//        //设置全部数据源
//        super.setTargetDataSources(targetDataSources);
//        //刷新(即把targetDataSources刷到resolvedDataSources中去，resolvedDataSources才是我们真正存放数据源的map)
//        super.afterPropertiesSet();
//    }
//}
