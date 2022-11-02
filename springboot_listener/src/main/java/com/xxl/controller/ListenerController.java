package com.xxl.controller;


import com.xxl.utils.AnnotationConfigApplicationContext;
import org.springframework.context.ApplicationEvent;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xxl
 * @date 2022/7/28 23:37
 */
@RestController
public class ListenerController {

    @RequestMapping("/test")
    public Object test(String name) {
        String result = "hello " + name;
//        test02();
        return result;
    }

    public static void main(String[] args) {
//    public void test02() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        // 手动发布一个事件
        ctx.publishEvent(new ApplicationEvent("我手动发布了一个事件") {
            @Override
            public Object getSource() {
                return super.getSource();
            }
        });
//        // 容器关闭也发布事件
//        ctx.close();

        ApplicationEvent applicationEvent = new ApplicationEvent("我手动发布了一个事件") {
            @Override
            public Object getSource() {
                return super.getSource();
            }
        };
        ctx.publishEvent(applicationEvent);
    }


}
