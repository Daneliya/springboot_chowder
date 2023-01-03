package com.xxl.itlaoge.handler;

import org.springframework.beans.factory.InitializingBean;

/**
 * 策略设计模式
 *
 * @author xxl
 * @date 2023/1/3 23:43
 */
public interface Handler extends InitializingBean {

    public void AAA(String name);

}
