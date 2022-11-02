package com.xxl.utils;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * 对 request 对象进行监听（创建、销毁）
 *
 * @author xxl
 * @date 2022/7/28 23:37
 */
@WebListener
public class MyServletRequestListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        System.out.println("监听器销毁。。。");
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        System.out.println("监听器初始化。。。");
        String name = servletRequestEvent.getServletRequest().getParameter("name");
        System.out.println(name);
    }

}