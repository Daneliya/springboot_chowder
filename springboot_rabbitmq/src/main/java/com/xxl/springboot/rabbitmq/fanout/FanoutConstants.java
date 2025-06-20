package com.xxl.springboot.rabbitmq.fanout;

/**
 * 广播消息标识定义
 *
 * @Author: xxl
 * @Date: 2024/11/8 10:14
 */
public interface FanoutConstants {

    /**
     * 广播消息交换机
     */
    String FANOUT_EXCHANGE = "demo.fanout.exchange";

    /**
     * 广播消息队列1
     */
    String FANOUT_QUEUE_1 = "demo1.fanout.queue";

    /**
     * 广播消息队列2
     */
    String FANOUT_QUEUE_2 = "demo2.fanout.queue";

    /**
     * 队列1失效时间
     */
    Long queue1_expire_mills = 30 * 60 * 1000L;

    /**
     * 队列2失效时间
     */
    Long queue2_expire_mills = 5 * 60 * 1000L;

    /**
     * 死信交换机
     */
    String DEADLETTER_EXCHANGE = "deadletter.exchange";

    /**
     * 死信队列
     */
    String DEADLETTER_QUEUE = "deadletter.queue";

    /**
     * 死信路由键
     */
    String DEADLETTER_ROUTING_KEY = "deadletter.routingkey";
}
