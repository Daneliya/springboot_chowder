package com.xxl.itlaoge.handler2;

import org.springframework.beans.factory.InitializingBean;

/**
 * 策略设计模式
 *
 * @author xxl
 * @date 2023/1/4 0:31
 */
public abstract class AbstractHandler implements InitializingBean {

    public void AAA(String name) {
        throw new UnsupportedOperationException();
    }

    public String BBB(String name) {
        throw new UnsupportedOperationException();
    }

}
