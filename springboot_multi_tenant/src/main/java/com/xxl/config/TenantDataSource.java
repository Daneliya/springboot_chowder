package com.xxl.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.xxl.config.dao.DataSourceConfigDao;
import com.xxl.config.vo.DataSourceConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 租户数据源信息
 *
 * @author xxl
 * @date 2022/5/5 23:55
 */
@Component  //https://blog.csdn.net/weixin_38168484/article/details/101534255
public class TenantDataSource {

    //容器，用来存储租户数据源信息
    private final Map<String, DataSource> dataSources = new HashMap<>();

    @Resource
    private DataSourceConfigDao dataSourceConfigDao;

    public Map<String, DataSource> getAll() {
        List<DataSourceConfig> list = dataSourceConfigDao.getAll();
        Map<String, DataSource> result = new HashMap<>();
        for (DataSourceConfig config : list) {
            DataSource dataSource = getDataSource(config);
            result.put(config.getName(), dataSource);
        }
        return result;
    }

    public DataSource getDataSource(DataSourceConfig config) {
        if (dataSources.containsKey(config.getName())) {
            return dataSources.get(config.getName());
        }
        DataSource dataSource = createDataSource(config);
        dataSources.put(config.getName(), dataSource);
        return dataSource;
    }

    private DataSource createDataSource(DataSourceConfig config) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        dataSource.setUrl(config.getUrl());
        dataSource.setDriverClassName(config.getDriverClassName());
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());
        return dataSource;
    }
}
