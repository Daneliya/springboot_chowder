package com.xxl.aynsc.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author: xxl
 * @Date: 2024/11/22 15:17
 */
@Component
public class AynscService {


    @Async
    public void sendAynscMessage(Integer index) {
        System.out.println("发送异步消息，第" + index + "次！");
    }

}
