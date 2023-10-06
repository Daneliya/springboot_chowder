package com.xxl.encrypt_hutool.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * @Description: SM4 国米加密测试类
 * @Author: xxl
 * @Date: 2023/08/18 9:02
 * @Version: 1.0
 */
public class Sm4Controller {


    public static void main(String[] args) {
        test01();
        test02();
    }

    public static void test01() {
        String content = "test中文";
        SymmetricCrypto sm4 = SmUtil.sm4();

        String encryptHex = sm4.encryptHex(content);
        String decryptStr = sm4.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);

        System.out.println("公钥加密 " + encryptHex);
        System.out.println("私钥解密 " + decryptStr);
    }

    public static void test02() {
        String content = "内容";
        //获取初始化key
        String key = initKey();
//        String key = "QBBo4bHjs8JhvzNpNoDChw==";

        byte[] bytes = Base64.decodeBase64(key);
        //初始化sm4工厂
        SymmetricCrypto sm4 = SmUtil.sm4(bytes);

        //加密
        String encryptHex = sm4.encryptHex(content);
        //解密
        String decryptStr = sm4.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println("key " + key);
        System.out.println("公钥加密 " + encryptHex);
        System.out.println("私钥解密 " + decryptStr);
    }

    /**
     * 初始化key
     *
     * @return
     */
    public static String initKey() {
        SymmetricCrypto sm4 = SmUtil.sm4();
        byte[] bytes = sm4.getSecretKey().getEncoded();
        String x = Base64.encodeBase64String(bytes);
        System.out.println(x);
        return x;
    }

}
