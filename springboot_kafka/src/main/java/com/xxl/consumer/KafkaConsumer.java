package com.xxl.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费者
 *
 * @author xxl
 * @date 2022/5/10 0:14
 */
@Component
public class KafkaConsumer {

    @KafkaListener(topics = {"springboot_kafka"})
    public void consumer(String message) {
        System.out.printf("消费者接受到消息：%s", message);
    }

}
