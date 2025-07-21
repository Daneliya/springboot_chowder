package com.xxl.springboot.rabbitmq.tcmp;

import com.xxl.springboot.rabbitmq.fanout.FanoutConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 广播消息消费者
 *
 * @Author: xxl
 * @Date: 2025/6/20 15:40
 */
@Component
public class TcmpConsumer {

    @RabbitListener(queues = "demo1.leave.queue")
    public void listenFanoutQueue1(Object msg) {
        System.out.println("消费者1接收到Fanout消息：【" + msg + "】");
    }

}
