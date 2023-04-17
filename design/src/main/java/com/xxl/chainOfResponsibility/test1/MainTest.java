package com.xxl.chainOfResponsibility.test1;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/04/07 15:06
 * @Version: 1.0
 */
public class MainTest {

    public static void main(String[] args) {
        AbstractHandler handler1 = new Handler1();
        AbstractHandler handler2 = new Handler2();

        handler1.nextHandler = handler2;

        System.out.println(handler1.handlerRequest("handler2"));
    }
}
