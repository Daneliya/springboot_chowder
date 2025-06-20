package com.xxl.springboot.rabbitmq.work;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * 任务队列消息消费者
 *
 * @Author: xxl
 * @Date: 2024/10/15 14:30
 */
@Component
public class WorkConsumer {

    @RabbitListener(queues = WorkConstants.WORK_QUEUE)
    public void listenWorkQueue1(String msg) throws InterruptedException {
        System.out.println("任务队列消费者1接收到消息：【" + msg + "】" + LocalTime.now());
        Thread.sleep(20);
    }

    @RabbitListener(queues = WorkConstants.WORK_QUEUE)
    public void listenWorkQueue2(String msg) throws InterruptedException {
        System.err.println("任务队列消费者2接收到消息：【" + msg + "】" + LocalTime.now());
        Thread.sleep(200);
    }
}