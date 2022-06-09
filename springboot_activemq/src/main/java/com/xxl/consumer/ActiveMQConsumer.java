package com.xxl.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * 消息消费者
 *
 * @author xxl
 * @date 2022/5/14 20:48
 */
@Service
public class ActiveMQConsumer {

    @JmsListener(destination = "springboot_activemq")
    public void consumer(String message) {
        System.out.printf("消费者收到消息：%s", message);
    }

}