package com.xxl.springboot.rabbitmq.simple;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @Author: xxl
 * @Date: 2024/10/15 14:32
 */
@Configuration
public class SimpleRabbitMQConfig {

    public static final String SIMPLE_QUEUE_NAME = "simple.queue";

    @Bean
    public Queue immediateQueue() {
        return new Queue(SIMPLE_QUEUE_NAME);
    }

}
