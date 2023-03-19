package com.xxl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

/**
 * @Description: 配置加载拦截器 注意：这种方式会屏蔽掉SpringBoot对SpringMVC的自动配置！
 * @Author: xxl
 * @Date: 2023/02/15 23:25
 * @Version: 1.0
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 这里注意不要用new的DataSourceInterceptor ，因为那样不会添加容器中的那个拦截器，而是添加的new的拦截器
     * 所以 从容器中获取 全局唯一
     */
    @Resource
    private DataSourceInterceptor dataSourceInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(dataSourceInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}

