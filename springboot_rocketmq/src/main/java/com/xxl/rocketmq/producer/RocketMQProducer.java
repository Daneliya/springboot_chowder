package com.xxl.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息发送者
 *
 * @author xxl
 * @date 2022/12/11 19:19
 */
@Configuration
public class RocketMQProducer {

    @Value("${rocketmq.producer.groupName}")
    private String groupName;

    @Value("${rocketmq.namesrvAddr}")
    private String nameserAddr;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public DefaultMQProducer getRocketMQProducer() {
        DefaultMQProducer producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(nameserAddr);
        producer.setVipChannelEnabled(false);
        return producer;
    }

}