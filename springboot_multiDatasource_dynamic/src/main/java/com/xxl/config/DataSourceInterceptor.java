package com.xxl.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.xxl.constant.DbConstants;
import com.xxl.pojo.DataSourceItem;
import com.xxl.service.DatasourceConfigService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: controller 拦截器
 * @Author: xxl
 * @Date: 2023/02/15 23:24
 * @Version: 1.0
 */
@Component
public class DataSourceInterceptor implements HandlerInterceptor {

    //从容器获取对象,切忌这里不要自己去new，那样的话会导致对象不一致从而引起数据不一致
    @Resource
    private DynamicDataSourceContext dynamicDataSourceContext;

    private static DatasourceConfigService datasourceConfigService = (DatasourceConfigService) ApplicationContextUtils.getBean("DatasourceConfigService");


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //todo 获取instanceId 可以由网关带来
        String instanceId = request.getHeader("instanceId");
        String requestURI = request.getRequestURI();
        StringBuffer requestURL = request.getRequestURL();
        System.out.println(requestURI + " " + requestURL.toString());
        if (instanceId == null || instanceId.equals("")) {
            // 租户为空，设置当前默认的数据库
            dynamicDataSourceContext.setDataSource(DbConstants.DEFAULT_DB1);
            System.out.println("当前数据源是:" + DynamicDataSourceContext.getDataSource());
            return true;
        }
        //注意这里要用容器中那个，与dataSource()中创建的是同一个(之前自己new一个，就一直找不到错误原因)
        Map<Object, DataSource> resolvedDataSourcesMap = dynamicDataSourceContext.getResolvedDataSources();
        Set<Object> dynamicDataSourceSet = resolvedDataSourcesMap.keySet();
        for (Object o : dynamicDataSourceSet) {
            System.out.println("所有数据源：" + o);
        }
        //这里的业务是判断现在租户的instanceId是否已纳入数据源,如果没有就去添加该租户的数据源
        // TODO 生产中可能会根据instanceId去查表，获取该租户的配置数据源信息(这里演示就自己写个数据源来添加)
        if (!dynamicDataSourceSet.contains(instanceId)) {
            //获取现在已有的数据源 TODO 根据业务添加或删除数据源
            Map<Object, Object> map = new ConcurrentHashMap<>();
            for (Object key : resolvedDataSourcesMap.keySet()) {
                map.put(key, resolvedDataSourcesMap.get(key));
            }
            //组装新的数据源(添加或不添加)--
            buildDataSources(map, instanceId);
            //刷新数据源
            dynamicDataSourceContext.freshDataSource(map);
        }
        //设置当前租户对应的数据库
        dynamicDataSourceContext.setDataSource(instanceId);
        System.out.println("当前数据源是:" + DynamicDataSourceContext.getDataSource());
        return true;
    }

    private void buildDataSources(Map<Object, Object> map, String instanceId) {
        System.out.println("新数据源" + instanceId);
        //TODO 远程获取数据源状态,如果是 启用状态则添加 否则不添加 如果已停用就删除
        dynamicDataSourceContext.setDataSource(DbConstants.DEFAULT_DB1);
        DataSourceItem oneDatasource = datasourceConfigService.getOneDatasource(instanceId);
        if (oneDatasource != null) {
            map.put(instanceId, buildDataSource(oneDatasource));
        }
//        int flag = 1;
//        if (instanceId.equals("4") && flag == 1) {
//            DataSourceItem ds = DataSourceItem
//                    .builder()
//                    .key(DbConstants.DEFAULT_DB4)
//                    .poolName(DbConstants.DEFAULT_DB4)
//                    .url("jdbc:mysql://127.0.0.1:3306/multi_tenant_slave?useSSL=false&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai")
//                    .username("root")
//                    .password("xxl666")
//                    .driverClassName("com.mysql.cj.jdbc.Driver")
//                    .build();
//
//            map.put(ds.getKey(), buildDataSource(ds));
//        } else {
//            //其他状态则认为数据源不可用-不添加
//        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        DynamicDataSourceContext.clearDataSource();
    }

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

