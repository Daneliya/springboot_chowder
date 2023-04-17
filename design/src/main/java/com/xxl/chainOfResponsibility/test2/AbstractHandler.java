package com.xxl.chainOfResponsibility.test2;

/**
 * @Description: 抽象处理者
 * @Author: xxl
 * @Date: 2023/04/07 15:25
 * @Version: 1.0
 */
public abstract class AbstractHandler {


    /**
     * 下一节点上的处理者对象
     */
    protected AbstractHandler nextHandler;


    /**
     * 处理请求
     *
     * @param request
     */
    public void handleRequest(AbstractRequest request) {
        if (getHandlerLevel() == request.getRequestLevel()) {
            handle(request);
        } else {
            if (nextHandler != null) {
                nextHandler.handleRequest(request);
            } else {
                System.out.println("所有对象都不能处理该请求");
            }
        }
    }

    /**
     * 处理请求
     *
     * @param request 请求对象
     */
    public abstract void handle(AbstractRequest request);

    /**
     * 每个处理者具体处理请求对象的实现
     *
     * @return 处理级别
     */
    public abstract int getHandlerLevel();


    /**
     * 设置下一个处理者
     *
     * @param nextHandler
     */
    public void setNextHandler(AbstractHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

}