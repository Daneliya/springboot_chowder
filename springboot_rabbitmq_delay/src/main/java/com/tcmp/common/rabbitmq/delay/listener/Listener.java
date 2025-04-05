package com.tcmp.common.rabbitmq.delay.listener;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static com.tcmp.common.rabbitmq.delay.config.DelayedRabbitMQConfig.DELAYED_QUEUE_NAME;

/**
 * 监听器
 *
 * @Author: xxl
 * @Date: 2024/10/14 17:33
 */
@Slf4j
@Component
public class Listener {

    @RabbitListener(queues = DELAYED_QUEUE_NAME)
    public void receiveD(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{},延时队列收到消息：{}", new Date().toString(), msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        Object object = JSON.parseObject(message.getBody(), Map.class);
        System.out.println("延时队列收到消息: " + object);
    }


}
