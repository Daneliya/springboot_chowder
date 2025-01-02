package com.xxl.springboot.init.config;

import com.xxl.springboot.init.util.MessageUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置bean
 *
 * @author xxl
 * @date 2024/12/31 22:28
 */
@Configuration
public class DefaultConfig {

    /**
     * 定义bean
     *
     * @return
     */
    @Bean(name = "messageUtil")
    public MessageUtil getMessageUtil() {
        // 配置bean对象
        return new MessageUtil();
    }
}
