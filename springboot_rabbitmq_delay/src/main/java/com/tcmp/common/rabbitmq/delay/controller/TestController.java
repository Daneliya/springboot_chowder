package com.tcmp.common.rabbitmq.delay.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.tcmp.common.rabbitmq.delay.config.DelayedRabbitMQConfig.DELAYED_EXCHANGE_NAME;
import static com.tcmp.common.rabbitmq.delay.config.DelayedRabbitMQConfig.DELAYED_ROUTING_KEY;

/**
 * @Author: xxl
 * @Date: 2024/10/14 17:32
 */
@Slf4j
@RestController
public class TestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("delayMsg")
    public Object delayMsg(String msg, Integer delayTime) {
        log.info("当前时间：{},收到请求，msg:{},delayTime:{}", new Date(), msg, delayTime);
//        sender.sendDelayMsg(msg, delayTime);

        // 准备消息
        for (int i = 1; i <= 10; i++) {
            Map<String, Object> msgMap = new HashMap<>();
            msgMap.put("name", "Jack" + i);
            msgMap.put("age", i);
            sendDelayMsg(msgMap, delayTime);
        }

        return "发送成功";
    }

    public void sendDelayMsg(Object msg, Integer delayTime) {
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, msg, a -> {
            a.getMessageProperties().setDelay(delayTime);
            return a;
        });
    }

}
