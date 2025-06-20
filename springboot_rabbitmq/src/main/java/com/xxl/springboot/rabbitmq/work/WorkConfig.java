package com.xxl.springboot.rabbitmq.work;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 任务队列 配置类
 *
 * @Author: xxl
 * @Date: 2024/10/15 14:32
 */
@Configuration
public class WorkConfig {

    @Bean
    public Queue immediateWorkQueue() {
        return new Queue(WorkConstants.WORK_QUEUE);
    }

}
