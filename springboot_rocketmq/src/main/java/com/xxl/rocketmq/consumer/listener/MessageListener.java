package com.xxl.rocketmq.consumer.listener;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 消息监听器
 *
 * @author xxl
 * @date 2022/12/11 19:23
 */
@Component
public class MessageListener implements MessageListenerConcurrently {

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        MessageExt ext = list.get(0);
        String msg = new String(ext.getBody());
        System.out.printf("消费者接受到消息：%s", msg);
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

}