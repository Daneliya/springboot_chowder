package com.xxl.user;

import org.junit.Test;

/**
 * @Description: 密码加密测试
 * @Author: xxl
 * @Date: 2023/07/06 10:23
 * @Version: 1.0
 */
public class PasswordTest {

    /**
     * 生成
     */
    @Test
    public void test01() {
//        String mobile = "13953991239";
        String mobile = "18661800609";
        String salt = PasswordUtils.getSalt();
        //生成密码
        String password = PasswordUtils.encode(mobile.substring(5, 11), salt);
        System.out.println("salt: " + salt);
        System.out.println("password: " + password);
    }
}
