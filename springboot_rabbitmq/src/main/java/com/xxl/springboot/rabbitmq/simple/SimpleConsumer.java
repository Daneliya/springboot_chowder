package com.xxl.springboot.rabbitmq.simple;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * 简单队列消息消费者
 *
 * @Author: xxl
 * @Date: 2024/10/15 14:30
 */
@Component
public class SimpleConsumer {

    @RabbitListener(queues = SimpleConstants.SIMPLE_QUEUE)
    public void listenSimpleQueue(String msg) throws InterruptedException {
        System.out.println("简单队列消费者接收到消息：【" + msg + "】" + LocalTime.now());
        Thread.sleep(20);
    }

}