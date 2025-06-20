//package com.xxl.springboot.rabbitmq.fanout;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.FanoutExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 广播消息 配置类（version1：简单绑定配置）
// *
// * @Author: xxl
// * @Date: 2025/6/20 15:39
// */
//@Configuration
//public class FanoutConfig {
//
//    /**
//     * 声明交换机
//     *
//     * @return Fanout类型交换机
//     */
//    @Bean
//    public FanoutExchange fanoutExchange() {
//        return new FanoutExchange(FanoutConstants.FANOUT_EXCHANGE);
//    }
//
//    /**
//     * 第1个队列
//     */
//    @Bean
//    public Queue fanoutQueue1() {
//        return new Queue(FanoutConstants.FANOUT_QUEUE_1);
//    }
//
//    /**
//     * 绑定队列和交换机
//     */
//    @Bean
//    public Binding bindingQueue1(Queue fanoutQueue1, FanoutExchange fanoutExchange) {
//        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
//    }
//
//    /**
//     * 第2个队列
//     */
//    @Bean
//    public Queue fanoutQueue2() {
//        return new Queue(FanoutConstants.FANOUT_QUEUE_2);
//    }
//
//    /**
//     * 绑定队列和交换机
//     */
//    @Bean
//    public Binding bindingQueue2(Queue fanoutQueue2, FanoutExchange fanoutExchange) {
//        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
//    }
//}