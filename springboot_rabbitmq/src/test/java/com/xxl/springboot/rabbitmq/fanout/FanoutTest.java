package com.xxl.springboot.rabbitmq.fanout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 广播消息 测试
 *
 * @Author: xxl
 * @Date: 2025/6/20 15:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FanoutTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testFanoutExchange() {
        // 队列名称
        String exchangeName = FanoutConstants.FANOUT_EXCHANGE;
        // 消息
        String message = "hello, everyone!";
        rabbitTemplate.convertAndSend(exchangeName, "", message);
    }
}
