package com.xxl.producer;

import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Destination;

/**
 * 消息发送者
 *
 * @author xxl
 * @date 2022/5/14 20:44
 */
@Component
public class ActiveMQProducer {

    @Resource
    private JmsMessagingTemplate messagingTemplate;

    public void send(Destination destination, String message) {
        messagingTemplate.convertAndSend(destination, message);
    }

}
