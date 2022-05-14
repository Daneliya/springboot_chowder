package com.xxl.controller;

import com.xxl.producer.ActiveMQProducer;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.jms.Destination;

/**
 * @author xxl
 * @date 2022/5/14 20:46
 */
public class DemoController {

    @Resource
    private ActiveMQProducer producer;

    /**
     * 发送消息接口
     *
     * @param msg
     * @return
     */
    @RequestMapping("/send")
    public String send(String msg) {
        Destination destination = new ActiveMQQueue("springboot_activemq");
        producer.send(destination, msg);
        return "发送成功！";
    }

}
