package com.xxl.util;

/**
 * @author xxl
 * @date 2022/12/22 22:12
 */
public abstract class UserUtils {

    //线程变量，存放user实体类信息，即使是静态的与其他线程也是隔离的
    private static final ThreadLocal<Integer> userThreadLocal = new ThreadLocal<>();

    //获取当前登录用户
    public static Integer getLoginUser() {
        return userThreadLocal.get();
    }

    public static void setLoginUser(Integer userId) {
        userThreadLocal.set(userId);
    }

    public static void removeUser(){
        userThreadLocal.remove();
    }

}