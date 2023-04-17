package com.xxl.chainOfResponsibility.test1;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/04/07 15:02
 * @Version: 1.0
 */
public abstract class AbstractHandler {

    /**
     * 下一个节点
     */
    protected AbstractHandler nextHandler;

    /**
     * 处理时间
     *
     * @param request 条件
     * @return
     */
    public abstract String handlerRequest(String request);

}