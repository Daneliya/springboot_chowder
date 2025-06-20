package com.xxl.springboot.rabbitmq.fanout;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 广播消息 配置类（version3：超时消息自动转发到“死信队列”）
 *
 * @Author: xxl
 * @Date: 2025/6/20 16:39
 */
@Configuration
public class FanoutDLQConfig {

    /**
     * 定义死信交换机
     *
     * @return Direct类型交换机
     */
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(FanoutConstants.DEADLETTER_EXCHANGE);
    }

    /**
     * 定义死信队列
     */
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(FanoutConstants.DEADLETTER_QUEUE)
                .build();
    }

    /**
     * 绑定死信队列到死信交换机
     */
    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue)
                .to(deadLetterExchange)
                .with(FanoutConstants.DEADLETTER_ROUTING_KEY);
    }

    /**
     * 声明交换机
     *
     * @return Fanout类型交换机
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FanoutConstants.FANOUT_EXCHANGE);
    }

    /**
     * 第1个队列，带消息超时时间和死信交换机配置
     */
    @Bean
    public Queue fanoutQueue1() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", FanoutConstants.queue1_expire_mills); // 30分钟 = 1800000 ms
        args.put("x-dead-letter-exchange", FanoutConstants.DEADLETTER_EXCHANGE); // 设置死信交换机
        args.put("x-dead-letter-routing-key", FanoutConstants.DEADLETTER_ROUTING_KEY); // 设置死信路由键

        return QueueBuilder.durable(FanoutConstants.FANOUT_QUEUE_1)
                .withArguments(args)
                .build();
    }

    /**
     * 绑定第1个队列和交换机
     */
    @Bean
    public Binding bindingQueue1(Queue fanoutQueue1, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

    /**
     * 第2个队列，也可以设置不同的TTL
     */
    @Bean
    public Queue fanoutQueue2() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", FanoutConstants.queue2_expire_mills); // 5分钟 = 300000 ms
        args.put("x-dead-letter-exchange", FanoutConstants.DEADLETTER_EXCHANGE);
        args.put("x-dead-letter-routing-key", FanoutConstants.DEADLETTER_ROUTING_KEY);

        return QueueBuilder.durable(FanoutConstants.FANOUT_QUEUE_2)
                .withArguments(args)
                .build();
    }

    /**
     * 绑定第2个队列和交换机
     */
    @Bean
    public Binding bindingQueue2(Queue fanoutQueue2, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }
}
