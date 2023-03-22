package com.xxl.config;

import com.xxl.handler.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 实现WebMvcConfigurer接口可以简化开发，但具有一定的侵入性
 *
 * @author xxl
 * @date 2022/12/22 21:35
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private MyInterceptor myInterceptor;

    // 拦截器 添加拦截器并设定拦截的访问路径， 路径可以通过可变参数设置多个

    /**
     * addInterceptor：需要一个实现HandlerInterceptor接口的拦截器实例
     * addPathPatterns：用于设置拦截器的过滤路径规则；addPathPatterns("/**")对所有请求都拦截
     * excludePathPatterns：用于设置不需要拦截的过滤规则
     * 拦截器主要用途：进行用户登录状态的拦截，日志的拦截等。
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 配置拦截器
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/**")// 需要拦截的路径（/** 拦截所有路径）
                .excludePathPatterns("/login");//放行静态资源和登陆资源;

    }
}