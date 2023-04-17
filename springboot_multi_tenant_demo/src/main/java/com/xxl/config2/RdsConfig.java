package com.xxl.config2;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 租户rds切换类，业务切换数据源时统一调用此类
 * @Author: xxl
 * @Date: 2023/02/13 9:24
 * @Version: 1.0
 */
@Data
public class RdsConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 数据库URL
     */
    private String dbUrl;

    /**
     * 数据库端口
     */
    private String dbPort;

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 数据库账号
     */
    private String dbAccount;

    /**
     * 数据库密码
     */
    private String dbPassword;
}