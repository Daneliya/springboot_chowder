package com.xxl.pojo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description: 读取 application-druid.yml 配置
 * @Author: xxl
 * @Date: 2023/02/28 10:12
 * @Version: 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DruidYmlDataSource {

    private Map<String, DruidYmlDataSource.Properties> datasource;

    @Data
    public static class Properties {
        private String url;
        private String username;
        private String password;
        private String driverClassName;
        private String type;
    }

}
