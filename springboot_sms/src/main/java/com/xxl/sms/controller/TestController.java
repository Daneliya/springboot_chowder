package com.xxl.sms.controller;

import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xxl
 * @date 2024/1/15 1:10
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public void testSend() {
        //在创建完SmsBlend实例后，再未手动调用注销的情况下框架会持有该实例，可以直接通过指定configId来获取想要的配置，如果你想使用
        //负载均衡形式获取实例，只要使用getSmsBlend的无参重载方法即可，如果你仅有一个配置，也可以使用该方法
        SmsBlend smsBlend = SmsFactory.getSmsBlend("在配置中定义的configId");
        SmsResponse smsResponse = smsBlend.sendMessage("18888888888", "123");

        smsBlend.delayedMessage("18888888888", "123", 10L);
    }


}
