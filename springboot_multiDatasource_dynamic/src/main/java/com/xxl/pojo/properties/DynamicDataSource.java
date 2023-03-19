package com.xxl.pojo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description: 读取 application-dynamic.yml 配置
 * @Author: xxl
 * @Date: 2023/02/18 15:00
 * @Version: 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource.dynamic")
public class DynamicDataSource {
    
    private Map<String, Properties> datasource;

    @Data
    public static class Properties {
        private String url;
        private String username;
        private String password;
        private String driverClassName;
        private String type;
    }

}
