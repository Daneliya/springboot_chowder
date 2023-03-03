package com.xxl.pojo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: 默认数据源
 * @Author: xxl
 * @Date: 2023/02/17 14:44
 * @Version: 1.0
 */
@Data
@Component
//@PropertySource(value = "classpath:application-dynamic.yml")
//@ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.master")
@ConfigurationProperties(prefix = "spring.datasource.druid.datasource.master")
public class MasterDataSource {

    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private String type;

}
