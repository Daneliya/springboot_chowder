package com.xxl.config2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: rds切换服务类
 * @Author: xxl
 * @Date: 2023/02/13 9:25
 * @Version: 1.0
 */
@Service
@Slf4j
public class TenantRdsServiceImpl implements TenantRdsService {
    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private RdsMapper rdsMapper;

    /**
     * 获取rds配置
     *
     * @param tenantCode
     * @date 2021/8/28 13:53
     **/
    @Override
    public RdsConfig getRdsConfig(String tenantCode) {
        // 根据租户代码取租户表
        Tenant tenant = tenantMapper.selectByTenantCode(tenantCode);
        if (null == tenant) {
            return null;
        }
        // 取rds表
        Rds rds = rdsMapper.selectByPrimaryKey(tenant.getRdsId());
        if (null == rds) {
            return null;
        }
        // 转换为rds配置
        RdsConfig rdsConfig = new RdsConfig();
        rdsConfig.setDbUrl(rds.getHost());
        rdsConfig.setTenantCode(tenantCode);
        rdsConfig.setDbName(tenant.getDbName());
        rdsConfig.setDbAccount(rds.getAccount());
        rdsConfig.setDbPassword(rds.getPwd());
        rdsConfig.setDbPort(String.valueOf(rds.getPort()));
        return rdsConfig;
    }

    /**
     * 根据租户代码切换rds连接，同一个线程内rds配置只会查一次
     *
     * @param tenantCode
     * @date 2021/8/28 13:16
     **/
    @Override
    public void switchRds(String tenantCode) {
        if (StringUtils.isBlank(tenantCode)) {
            throw new TenantCodeIsBlankException();
        }
        // 如果当前已是这个租户rds则直接返回
        if (tenantCode.equals(DynamicDataSource.getDataSourceKey())) {
            return;
        }
        // 如果本地已有则不查了 改rds需要重启服务
        if (null == DynamicDataSource.getDataSourceMap(tenantCode)) {
            // 如果当前不是配置库则先切回配置库
            if (!DataSourceConstant.DATA_SOURCE_MASTER.equals(DynamicDataSource.getDataSourceKey())) {
                DynamicDataSource.setDataSourceDefault();
            }
            // 获取rds配置
            RdsConfig rdsConfig = getRdsConfig(tenantCode);
            if (null == rdsConfig) {
                throw new RdsNotFoundException();
            }
            DynamicDataSource.setDataSourceMap(rdsConfig);
        }
        // 切换到业务库
        DynamicDataSource.setDataSource(tenantCode);
    }

    /**
     * 根据数据源名称切换rds连接，同一个线程内rds配置只会查一次
     *
     * @param dataSourceName
     * @date 2021/8/28 13:16
     **/
    @Override
    public void switchRdsByDataSourceName(String dataSourceName) {
        if (StringUtils.isBlank(dataSourceName)) {
            throw new DataSourceNameIsEmptyException();
        }
        // 如果当前已是这个数据源直接返回
        if (dataSourceName.equals(DynamicDataSource.getDataSourceKey())) {
            return;
        }
        // 如果本地已有则不查了 改rds需要重启服务
        if (null == DynamicDataSource.getDataSourceMap(dataSourceName)) {
            throw new DataSourceNotExistException();
        }
        // 切换
        DynamicDataSource.setDataSource(dataSourceName);
    }
}