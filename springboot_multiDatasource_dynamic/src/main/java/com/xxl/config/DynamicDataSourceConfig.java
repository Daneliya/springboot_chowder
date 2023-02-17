package com.xxl.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.xxl.pojo.DataSourceItem;
import com.xxl.service.DatasourceConfigService;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 配置及初始化多数据源类
 * @Author: xxl
 * @Date: 2023/02/15 23:23
 * @Version: 1.0
 */
//@Configuration
@Component
public class DynamicDataSourceConfig {

    // 通过这种方法进行调用
    private static DatasourceConfigService datasourceConfigService = (DatasourceConfigService) ApplicationContextUtils.getBean("DatasourceConfigService");

//    @Autowired
//    public void setDatasourceConfigService(DatasourceConfigService datasourceConfigService) {
//        this.datasourceConfigService = datasourceConfigService;
//    }

    /**
     * 把DynamicDataSourceContext 纳入容器管理，其他地方使用DynamicDataSourceConfig 类可以直接从容器取对象，并调用freshDataSource方法
     */
    @Bean
    // @Primary
    public static DynamicDataSourceContext dataSource() {
        System.out.println("加载所有数据源---");
        Map<Object, Object> targetDataSource = getDataSource();
        // 把DynamicDataSourceContext纳入容器管理
        DynamicDataSourceContext dynamicDataSourceContext = new DynamicDataSourceContext();
        dynamicDataSourceContext.freshDataSource(targetDataSource);
        return dynamicDataSourceContext;
    }

    /**
     * 构建初始化数据源 TODO 生成中去其他地方获取初始化数据源(例如:表里面获取)
     *
     * @return
     */
    public static Map<Object, Object> getDataSource() {
        // FIXME-方式一：代码中定义配置
//        DataSourceItem ds1 = DataSourceItem
//                .builder()
//                .key(DbConstants.DEFAULT_DB1)
//                .poolName(DbConstants.DEFAULT_DB1)
//                .url("jdbc:mysql://127.0.0.1:3306/multi_tenant_master?useSSL=false&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai")
//                .username("root")
//                .password("xxl666")
//                .driverClassName("com.mysql.cj.jdbc.Driver")
//                .build();
//        DataSourceItem ds2 = DataSourceItem
//                .builder()
//                .key(DbConstants.DEFAULT_DB2)
//                .poolName(DbConstants.DEFAULT_DB2)
//                .url("jdbc:mysql://127.0.0.1:3306/multi_tenant_slave?useSSL=false&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai")
//                .username("root")
//                .password("xxl666")
//                .driverClassName("com.mysql.cj.jdbc.Driver")
//                .build();
//        DataSourceItem ds3 = DataSourceItem
//                .builder()
//                .key(DbConstants.DEFAULT_DB3)
//                .poolName(DbConstants.DEFAULT_DB3)
//                .url("jdbc:mysql://127.0.0.1:3306/multi_tenant_slave?useSSL=false&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai")
//                .username("root")
//                .password("xxl666")
//                .driverClassName("com.mysql.cj.jdbc.Driver")
//                .build();
//        Map<Object, Object> map = new HashMap<>();
//        map.put(ds1.getKey(), buildDataSource(ds1));
//        map.put(ds2.getKey(), buildDataSource(ds2));
//        map.put(ds3.getKey(), buildDataSource(ds3));
//        // 初始化数据放进本地数据源map
//        DynamicDataSourceContext.dataSourceMap.put(ds1.getKey(), buildDataSource(ds1));
//        DynamicDataSourceContext.dataSourceMap.put(ds2.getKey(), buildDataSource(ds2));
//        DynamicDataSourceContext.dataSourceMap.put(ds3.getKey(), buildDataSource(ds3));
        // FIXME-方式二：查询数据库中的配置（需要先配置默认的数据源，并作为主要优先级）
        List<DataSourceItem> allDatasource = DynamicDataSourceConfig.datasourceConfigService.getAllDatasource();

        Map<Object, Object> map = allDatasource.stream().collect(Collectors.toMap(DataSourceItem::getKey, e -> buildDataSource(e)));
        for (DataSourceItem dataSourceItem : allDatasource) {
            System.out.println("数据源：" + dataSourceItem.getKey() + " " + dataSourceItem);
            DynamicDataSourceContext.dataSourceMap.put(dataSourceItem.getKey(), buildDataSource(dataSourceItem));
        }
        // FIXME-方式三：获取yml中的默认配置

        return map;
    }

    /**
     * 把数据源对象组装成HikariDataSource
     *
     * @param dataSourceItem
     * @return
     */
    private static Object buildDataSource(DataSourceItem dataSourceItem) {
        // HikariDataSource dataSource = new HikariDataSource();
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(dataSourceItem.getUrl());
        dataSource.setUsername(dataSourceItem.getUsername());
        dataSource.setPassword(dataSourceItem.getPassword());
        dataSource.setDriverClassName(dataSourceItem.getDriverClassName());
        return dataSource;
    }
}
