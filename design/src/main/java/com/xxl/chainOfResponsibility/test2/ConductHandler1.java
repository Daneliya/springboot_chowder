package com.xxl.chainOfResponsibility.test2;

/**
 * @Description: 具体处理者类
 * @Author: xxl
 * @Date: 2023/04/07 15:26
 * @Version: 1.0
 */
public class ConductHandler1 extends AbstractHandler {

    @Override
    public void handle(AbstractRequest request) {
        System.out.println("Handler1 handler request :" + request.getRequestLevel());
    }

    @Override
    public int getHandlerLevel() {
        return 1;
    }
}
