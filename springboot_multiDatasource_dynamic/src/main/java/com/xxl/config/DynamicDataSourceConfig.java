package com.xxl.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.xxl.pojo.DataSourceItem;
import com.xxl.pojo.properties.DruidYmlDataSource;
import com.xxl.pojo.properties.DynamicDataSource;
import com.xxl.pojo.properties.MasterDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 配置及初始化多数据源类
 * @Author: xxl
 * @Date: 2023/02/15 23:23
 * @Version: 1.0
 */
//@Configuration
@Component
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) // 排除 DataSourceAutoConfiguration 的自动配置，避免环形调用
public class DynamicDataSourceConfig {

    @Autowired
    private MasterDataSource masterDataSource;

    @Autowired
    private DynamicDataSource dynamicDataSource;

    @Autowired
    private DruidYmlDataSource druidYmlDataSource;

    // 通过这种方法进行调用
//    private static DatasourceConfigService datasourceConfigService = (DatasourceConfigService) ApplicationContextUtils.getBean("DatasourceConfigService");

    /**
     * 把DynamicDataSourceContext 纳入容器管理，其他地方使用DynamicDataSourceConfig 类可以直接从容器取对象，并调用freshDataSource方法
     */
    @Bean
    @Primary
    public DynamicDataSourceContext dataSource() {
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
    public Map<Object, Object> getDataSource() {
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
//        List<DataSourceItem> allDatasource = DynamicDataSourceConfig.datasourceConfigService.getAllDatasource();
//
//        Map<Object, Object> map = allDatasource.stream().collect(Collectors.toMap(DataSourceItem::getKey, e -> buildDataSource(e)));
//        for (DataSourceItem dataSourceItem : allDatasource) {
//            System.out.println("数据源：" + dataSourceItem.getKey() + " " + dataSourceItem);
//            DynamicDataSourceContext.dataSourceMap.put(dataSourceItem.getKey(), buildDataSource(dataSourceItem));
//        }
        // FIXME-方式三：通过@ConfigurationProperties，获取yml中的配置
        //  （去掉默认数据源，启动类增加@EnableConfigurationProperties，DynamicDataSourceConfig 类及类中 dataSource 方法增加@RefreshScope支持监听配置动态刷新）
        System.out.println("masterDataSource " + masterDataSource);
        System.out.println("dynamicDataSource " + dynamicDataSource);
        System.out.println("druidYmlDataSource " + druidYmlDataSource);
        Map<Object, Object> map = new HashMap<>();
        for (String name : druidYmlDataSource.getDatasource().keySet()) {
            DruidYmlDataSource.Properties properties = druidYmlDataSource.getDatasource().get(name);
            DataSourceItem ds = DataSourceItem
                    .builder()
                    .key(name)
                    .poolName(name)
                    .url(properties.getUrl())
                    .username(properties.getUsername())
                    .password(properties.getPassword())
                    .driverClassName(properties.getDriverClassName())
                    .build();
            try {
                map.put(ds.getKey(), buildDataSource(ds, druidYmlDataSource));
                DynamicDataSourceContext.dataSourceMap.put(ds.getKey(), buildDataSource(ds, druidYmlDataSource));
            } catch (SQLException e) {
                throw new RuntimeException("【组装DruidDataSource数据源错误】{}", e);
            }
        }
        return map;
    }

    /**
     * 把数据源对象组装成DruidDataSource
     *
     * @param druidYmlDataSource
     * @param dataSourceItem
     * @return
     */
    private static DataSource buildDataSource(DataSourceItem dataSourceItem, DruidYmlDataSource druidYmlDataSource) throws SQLException {

        // HikariDataSource dataSource = new HikariDataSource();
        // 必要信息
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(dataSourceItem.getUrl());
        dataSource.setUsername(dataSourceItem.getUsername());
        dataSource.setPassword(dataSourceItem.getPassword());
        dataSource.setDriverClassName(dataSourceItem.getDriverClassName());
        // 其他配置信息
        dataSource.setName(druidYmlDataSource.getName());
        dataSource.setInitialSize(druidYmlDataSource.getInitialSize());
        dataSource.setMinIdle(druidYmlDataSource.getMinIdle());
        dataSource.setMaxActive(druidYmlDataSource.getMaxActive());
        dataSource.setMaxWait(druidYmlDataSource.getMaxWait());
        dataSource.setTimeBetweenEvictionRunsMillis(druidYmlDataSource.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(druidYmlDataSource.getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery(druidYmlDataSource.getValidationQuery());
        dataSource.setTestWhileIdle(druidYmlDataSource.getTestWhileIdle());
        dataSource.setTestOnBorrow(druidYmlDataSource.getTestOnBorrow());
        dataSource.setTestOnReturn(druidYmlDataSource.getTestOnReturn());
        dataSource.setPoolPreparedStatements(druidYmlDataSource.getPoolPreparedStatements());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(druidYmlDataSource.getMaxPoolPreparedStatementPerConnectionSize());
        dataSource.setFilters(druidYmlDataSource.getFilters());
        return dataSource;
    }
}
