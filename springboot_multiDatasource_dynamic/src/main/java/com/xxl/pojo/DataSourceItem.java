package com.xxl.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 数据源实体类
 * @Author: xxl
 * @Date: 2023/02/15 23:20
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataSourceItem {
    private String name;
    private String key;
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private String poolName;
}