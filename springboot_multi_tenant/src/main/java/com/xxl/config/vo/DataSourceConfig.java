package com.xxl.config.vo;

import lombok.Data;

/**
 * @author xxl
 * @date 2022/5/5 23:35
 */
@Data
public class DataSourceConfig {

    private Long id;
    private String name;
    private String url;
    private String username;
    private String password;
    private String driverClassName;

}
