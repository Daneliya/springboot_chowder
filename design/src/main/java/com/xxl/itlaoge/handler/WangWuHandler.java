package com.xxl.itlaoge.handler;

import org.springframework.stereotype.Component;

/**
 * @author xxl
 * @date 2023/1/3 23:44
 */
@Component
public class WangWuHandler implements Handler {
    @Override
    public void AAA(String name) {
        System.out.println(name + "完成了任务");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Factory.register("王五", this);
    }
}
