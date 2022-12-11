package com.xxl.rocketmq.controller;

import com.xxl.rocketmq.producer.RocketMQProducer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xxl
 * @date 2022/12/11 19:20
 */
@RestController
public class DemoController {

    @Resource
    private RocketMQProducer rocketMQProducer;

    @RequestMapping("/send")
    public String send(String msg) {
        DefaultMQProducer producer = rocketMQProducer.getRocketMQProducer();
        Message message = new Message("springboot_rocketmq", "test", msg.getBytes());
        try {
            producer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "发送成功";
    }

}
