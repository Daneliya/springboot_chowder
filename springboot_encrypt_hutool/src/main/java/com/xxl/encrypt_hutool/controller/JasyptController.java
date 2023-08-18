package com.xxl.encrypt_hutool.controller;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: jasypt 接口测试
 * @Author: xxl
 * @Date: 2023/05/31 11:31
 * @Version: 1.0
 */
@RestController
public class JasyptController {

    @Value("${com.name}")
    private String name;

    @Autowired
    private StringEncryptor encryptor;


    @RequestMapping("/get")
    public String get() {
        return name;
    }

    /**
     * 测试jasypt加密解密
     */
    @GetMapping("/jasypt")
    public void testJasypt() {
        String password = "123456";
        String encryptPwd = encryptor.encrypt(password);
        System.out.println("加密:：" + encryptPwd);
        System.out.println("解密：" + encryptor.decrypt(encryptPwd));
    }
}