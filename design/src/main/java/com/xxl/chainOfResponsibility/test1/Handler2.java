package com.xxl.chainOfResponsibility.test1;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/04/07 15:05
 * @Version: 1.0
 */
public class Handler2 extends AbstractHandler {

    @Override
    public String handlerRequest(String request) {
        if (request.equals("handler2")) {
            return "handler2";
        } else {
            return "handler2 + " + nextHandler.handlerRequest(request);
        }
    }
}