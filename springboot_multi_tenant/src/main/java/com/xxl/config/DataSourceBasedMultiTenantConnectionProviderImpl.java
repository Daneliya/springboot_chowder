package com.xxl.config;

import com.xxl.config.dao.DataSourceConfigDao;
import com.xxl.config.vo.DataSourceConfig;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xxl
 * @date 2022/5/6 0:06
 */
@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    @Resource(name = "defaultDataSource") // DefaultDataSourceConfig 中的 Bean
    private DataSource defaultDataSource;

    @Resource
    private ApplicationContext context;

    static Map<String, DataSource> map = new HashMap<>();

    //控制初始化次数
    public static boolean init = false;

    //类初始化之前初始一个默认数据源
    @PostConstruct
    public void load() {
        map.put("defaultDataSource", defaultDataSource);
    }

    @Override
    public DataSource selectAnyDataSource() {
        return map.get("defaultDataSource");
    }

    @Override
    public DataSource selectDataSource(String tenant) {
        if (!init) {
            init = true;
            TenantDataSource tenantDataSource = context.getBean(TenantDataSource.class);
            map.putAll(tenantDataSource.getAll());
        }
        return map.get(tenant);

        //懒加载
//        DataSource dataSource = map.get(tenant);
//        if (dataSource == null) {
//            TenantDataSource tenantDataSource = context.getBean(TenantDataSource.class);
//            DataSourceConfigDao dataSourceConfigDao = context.getBean(DataSourceConfigDao.class);
//            DataSourceConfig config = dataSourceConfigDao.getByName(tenant);
//            dataSource = tenantDataSource.getDataSource(config);
//            map.put(tenant, dataSource);
//        }
//        return dataSource;
    }
}
