package com.xxl.springboot.rabbitmq.direct;

/**
 * Direct消息标识定义
 *
 * @Author: xxl
 * @Date: 2024/11/8 10:14
 */
public interface DirectConstants {

    /**
     * Direct消息交换机
     */
    String DIRECT_EXCHANGE = "demo.direct.exchange";

    /**
     * Direct消息队列1
     */
    String DIRECT_QUEUE_1 = "demo1.direct.queue";

    /**
     * 死信路由键
     */
    String DIRECT_ROUTING_KEY = "demoKey";

    /**
     * 队列1失效时间
     */
    Long queue1_expire_mills = 30 * 60 * 1000L;
}
