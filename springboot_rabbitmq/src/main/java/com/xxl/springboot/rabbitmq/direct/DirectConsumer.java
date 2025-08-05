package com.xxl.springboot.rabbitmq.direct;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 广播消息消费者
 *
 * @Author: xxl
 * @Date: 2025/6/20 15:40
 */
@Component
public class DirectConsumer {

    @RabbitListener(queues = DirectConstants.DIRECT_QUEUE_1)
    public void listenDirectQueue1(String msg) {
        System.out.println("消费者1接收到Fanout消息：【" + msg + "】");
    }
}
