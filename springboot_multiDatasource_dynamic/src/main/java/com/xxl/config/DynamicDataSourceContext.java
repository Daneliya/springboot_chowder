package com.xxl.config;

import com.xxl.constant.DbConstants;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 扩展 Spring 的 AbstractRoutingDataSource 抽象类继承类
 * @Author: xxl
 * @Date: 2023/02/15 23:23
 * @Version: 1.0
 */
@Component
public class DynamicDataSourceContext extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 数据源存放本地map
     */
    public static Map<Object, DataSource> dataSourceMap = new ConcurrentHashMap<>();

    /**
     * 设置默认数据源、全部数据源，及刷新
     */
    public void freshDataSource(Map<Object, Object> targetDataSources) {
        //默认数据源
        super.setDefaultTargetDataSource(targetDataSources.get(DbConstants.DEFAULT_MAIN));
        //设置全部数据源
        super.setTargetDataSources(targetDataSources);
        //刷新(即把targetDataSources刷到resolvedDataSources中去，resolvedDataSources才是我们真正存放数据源的map)
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        //获取当前指定的数据源
        return getDataSource();
    }

    @Override
    public void afterPropertiesSet() {
    }

    public static String getDataSource() {
        return CONTEXT_HOLDER.get();
    }

    public static void setDataSource(String dataSource) {
        CONTEXT_HOLDER.set(dataSource);
    }

    public static void clearDataSource() {
        CONTEXT_HOLDER.remove();
    }
}
