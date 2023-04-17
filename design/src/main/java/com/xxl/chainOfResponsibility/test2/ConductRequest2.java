package com.xxl.chainOfResponsibility.test2;

/**
 * @Description: 具体处理者类
 * @Author: xxl
 * @Date: 2023/04/07 15:28
 * @Version: 1.0
 */
public class ConductRequest2 extends AbstractRequest {

    /**
     * 通过构造函数注入要处理的内容对象
     *
     * @param object 要处理的内容对象
     */
    public ConductRequest2(Object object) {
        super(object);
    }

    @Override
    public int getRequestLevel() {
        return 2;
    }
}