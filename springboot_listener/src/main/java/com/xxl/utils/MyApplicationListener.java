package com.xxl.utils;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


/**
 * @Author: xxl
 * @Date: 2022/11/02 10:28
 */
@Component
public class MyApplicationListener implements ApplicationListener {

    /**
     * 接受到消息，回调该方法
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("MyApplicationListener 接收到了一个事件" + event);
    }

}
