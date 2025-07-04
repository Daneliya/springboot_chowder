package com.xxl.springboot.rabbitmq.simple;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 简单队列 配置类
 *
 * @Author: xxl
 * @Date: 2024/10/15 14:32
 */
@Configuration
public class SimpleConfig {

    @Bean
    public Queue immediateSimpleQueue() {
        return new Queue(SimpleConstants.SIMPLE_QUEUE);
    }

}
