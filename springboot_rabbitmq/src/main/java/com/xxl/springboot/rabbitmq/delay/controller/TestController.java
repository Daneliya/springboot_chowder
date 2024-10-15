package com.xxl.springboot.rabbitmq.delay.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.xxl.springboot.rabbitmq.delay.config.DelayedRabbitMQConfig.DELAYED_EXCHANGE_NAME;
import static com.xxl.springboot.rabbitmq.delay.config.DelayedRabbitMQConfig.DELAYED_ROUTING_KEY;


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
    public void delayMsg(String msg, Integer delayTime) {
        log.info("当前时间：{},收到请求，msg:{},delayTime:{}", new Date(), msg, delayTime);
//        sender.sendDelayMsg(msg, delayTime);

        // 准备消息
        Map<String,Object> msgMap = new HashMap<>();
        msgMap.put("name", "Jack");
        msgMap.put("age", 21);
        sendDelayMsg(msgMap, delayTime);
    }

    public void sendDelayMsg(Object msg, Integer delayTime) {
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, msg, a ->{
            a.getMessageProperties().setDelay(delayTime);
            return a;
        });
    }

}
