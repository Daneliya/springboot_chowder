package com.xxl.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 消息消费者
 *
 * @author xxl
 * @date 2022/12/11 19:21
 */
@Configuration
public class RocketMQConsumer {

    @Resource
    private MessageListener messageListener;

    @Value("${rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.consumer.groupName}")
    private String groupName;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public DefaultMQPushConsumer getRocketMQConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setVipChannelEnabled(false);
        consumer.registerMessageListener(messageListener);
        try {
            consumer.subscribe("springboot_rocketmq", "test");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return consumer;
    }

}
