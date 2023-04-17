package com.xxl.chainOfResponsibility.test2;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/04/07 15:26
 * @Version: 1.0
 */
public abstract class AbstractRequest {

    /**
     * 要处理的内容对象
     */
    private Object object;

    /**
     * 通过构造函数注入要处理的内容对象
     *
     * @param object 要处理的内容对象
     */
    public AbstractRequest(Object object) {
        this.object = object;
    }

    /**
     * 获取要处理的内容对象
     *
     * @return 要处理的内容对象
     */
    public Object getObject() {
        return object;
    }

    /**
     * 获取请求级别
     *
     * @return 请求级别
     */
    public abstract int getRequestLevel();
}
