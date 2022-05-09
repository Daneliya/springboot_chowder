package com.xxl.controller;

import com.xxl.producer.KafkaProducer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xxl
 * @date 2022/5/10 0:11
 */
@RestController
public class DemoController {

    @Resource
    private KafkaProducer producer;

    /**
     * 发送消息接口
     *
     * @param msg
     * @return
     */
    @RequestMapping("/send")
    public String send(String msg) {
        producer.send("springboot_kafka", msg);
        return "发送成功";
    }

}