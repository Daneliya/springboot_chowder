package com.xxl.itlaoge.handler2;

import com.xxl.itlaoge.handler.Factory;
import com.xxl.itlaoge.handler.Handler;
import org.springframework.stereotype.Component;

/**
 * @author xxl
 * @date 2023/1/3 23:43
 */
@Component
public class ZhangSanHandler2 extends AbstractHandler {

    public String BBB(String name) {
        return name + "完成了任务";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Factory2.register("张三", this);
    }
}
