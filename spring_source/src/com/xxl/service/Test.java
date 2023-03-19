package com.xxl.service;

import com.xxl.spring.ApplicationContext;

/**
 * @author xxl
 * @date 2023/3/19 15:44
 */
public class Test {

    public static void main(String[] args) {
        // 创建一个容器，并传送一个配置类
        ApplicationContext applicationContext = new ApplicationContext(AppConfig.class);
        UserService userService = (UserService) applicationContext.getBean("userService");



    }

}
