package com.xxl.springboot.rabbitmq.direct;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Direct消息 配置类
 *
 * @Author: xxl
 * @Date: 2025/6/20 16:39
 */
@Configuration
public class DirectConfig {

    /**
     * 声明交换机
     *
     * @return Direct类型交换机
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DirectConstants.DIRECT_EXCHANGE);
    }

    /**
     * 第1个队列，带消息超时时间和死信交换机配置
     */
    @Bean
    public Queue directQueue1() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", DirectConstants.queue1_expire_mills); // 30分钟 = 1800000 ms

        return QueueBuilder.durable(DirectConstants.DIRECT_QUEUE_1)
                .withArguments(args)
                .build();
    }

    /**
     * 绑定第1个队列和交换机
     */
    @Bean
    public Binding bindingQueue1(Queue directQueue1, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue1).to(directExchange).with(DirectConstants.DIRECT_ROUTING_KEY);
    }

}
