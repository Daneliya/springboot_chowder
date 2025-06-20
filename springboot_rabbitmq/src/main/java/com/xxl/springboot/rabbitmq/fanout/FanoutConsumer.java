package com.xxl.springboot.rabbitmq.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 广播消息消费者
 *
 * @Author: xxl
 * @Date: 2025/6/20 15:40
 */
@Component
public class FanoutConsumer {

    @RabbitListener(queues = FanoutConstants.FANOUT_QUEUE_1)
    public void listenFanoutQueue1(String msg) {
        System.out.println("消费者1接收到Fanout消息：【" + msg + "】");
    }

//    @RabbitListener(queues = FanoutConstants.FANOUT_QUEUE_2)
//    public void listenFanoutQueue2(String msg) {
//        System.out.println("消费者2接收到Fanout消息：【" + msg + "】");
//    }

    @RabbitListener(queues = FanoutConstants.DEADLETTER_QUEUE)
    public void listenDeadletterQueue(String msg) {
        System.out.println("死信队列接收到过期消息：【" + msg + "】");
    }

}
