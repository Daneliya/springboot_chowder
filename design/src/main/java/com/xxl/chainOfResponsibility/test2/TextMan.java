package com.xxl.chainOfResponsibility.test2;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/04/07 15:28
 * @Version: 1.0
 */
public class TextMan {

    public static void main(String[] argc) {
        //不同级别的请求
        AbstractRequest request1 = new ConductRequest1("request 1");
        AbstractRequest request2 = new ConductRequest2("request 2");

        //不同级别的处理者
        AbstractHandler handler1 = new ConductHandler1();
        AbstractHandler handler2 = new ConductHandler2();

        // 设置处理者的链式关系
        handler1.setNextHandler(handler2);

        // 总是从链子的首端发起请求
        handler1.handleRequest(request1);
        handler1.handleRequest(request2);
    }
}