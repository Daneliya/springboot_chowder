package com.xxl.springboot.rabbitmq.work;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 任务队列 测试
 *
 * @Author: xxl
 * @Date: 2025/6/20 17:34
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * workQueue
     * 向队列中不停发送消息，模拟消息堆积。
     */
    @Test
    public void testWorkQueue() throws InterruptedException {
        // 队列名称
        String queueName = WorkConstants.WORK_QUEUE;
        // 消息
        String message = "hello, message_";
        for (int i = 0; i < 50; i++) {
            // 发送消息
            rabbitTemplate.convertAndSend(queueName, message + i);
            Thread.sleep(20);
        }
    }
}
