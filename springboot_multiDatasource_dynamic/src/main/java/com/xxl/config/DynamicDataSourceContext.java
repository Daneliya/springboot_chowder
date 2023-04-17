package com.xxl.config;

import com.xxl.constant.DbConstants;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 扩展 Spring 的 AbstractRoutingDataSource 抽象类继承类
 * @Author: xxl
 * @Date: 2023/02/15 23:23
 * @Version: 1.0
 */
//@Component
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

    /**
     * 可不可以不加 DynamicDataSourceContext.clearDataSource 这一步，不加会造成什么问题？
     *
     * 当您使用 DynamicDataSourceContext.setDataSource 切换数据源时，如果不清除数据源，可能会导致以下问题：
     *
     * 数据源泄漏：如果您不清除数据源，当前线程将继续使用该数据源，即使它已经不再需要。这可能会导致数据源泄漏，从而导致应用程序出现内存泄漏或连接池耗尽等问题。
     *
     * 数据源混淆：如果您不清除数据源，当前线程将继续使用该数据源，即使它已经不再需要。这可能会导致数据源混淆，从而导致应用程序出现意外行为或错误。
     *
     * 因此，建议在使用完数据源后调用 DynamicDataSourceContext.clearDataSource 来清除数据源。这将确保当前线程不再使用该数据源，并将其返回到连接池中，以便其他线程可以使用它。
     */
    public static void clearDataSource() {
        CONTEXT_HOLDER.remove();
    }
}
