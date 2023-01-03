package com.xxl.itlaoge.handler;

import org.springframework.stereotype.Component;

/**
 * @author xxl
 * @date 2023/1/3 23:43
 */
@Component
public class ZhangSanHandler implements Handler {
    @Override
    public void AAA(String name) {
        System.out.println(name + "完成了任务");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Factory.register("张三", this);
    }
}
