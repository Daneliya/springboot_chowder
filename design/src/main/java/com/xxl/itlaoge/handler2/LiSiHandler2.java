package com.xxl.itlaoge.handler2;

import com.xxl.itlaoge.handler.Factory;
import com.xxl.itlaoge.handler.Handler;
import org.springframework.stereotype.Component;

/**
 * @author xxl
 * @date 2023/1/3 23:43
 */
@Component
public class LiSiHandler2 extends AbstractHandler {

    @Override
    public void AAA(String name) {
        System.out.println(name + "完成了任务");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Factory2.register("李四", this);
    }
}
