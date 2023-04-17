package com.xxl.chainOfResponsibility.test1;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/04/07 15:05
 * @Version: 1.0
 */
public class Handler1 extends AbstractHandler {

    @Override
    public String handlerRequest(String request) {
        if (request.equals("handler1")) {
            return "handler1";
        } else {
            return "handler1 + " + nextHandler.handlerRequest(request);
        }
    }
}