package com.xxl.encrypt_hutool.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * @Description: 对称加密
 * @Author: xxl
 * @Date: 2023/06/02 14:15
 * @Version: 1.0
 */
public class SymmetricCryptoUtils {

    /**
     * 加密
     *
     * @param val
     * @return
     */
    public static String encrypt(String val) {
        //这里特别注意一下，对称加密是根据密钥进行加密和解密的，加密和解密的密钥是相同的，一旦泄漏，就无秘密可言，
        //“fanfu-csdn”就是我自定义的密钥，这里仅作演示使用，实际业务中，这个密钥要以安全的方式存储；
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.DES.getValue(), "fanfu-csdn".getBytes()).getEncoded();
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.DES, key);
        String encryptValue = aes.encryptBase64(val);
        return encryptValue;
    }

    /**
     * 解密
     *
     * @param val
     * @return
     */
    public static String decrypt(String val) {
        //这里特别注意一下，对称加密是根据密钥进行加密和解密的，加密和解密的密钥是相同的，一旦泄漏，就无秘密可言，
        //“fanfu-csdn”就是我自定义的密钥，这里仅作演示使用，实际业务中，这个密钥要以安全的方式存储；
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.DES.getValue(), "fanfu-csdn".getBytes()).getEncoded();
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.DES, key);
        String encryptValue = aes.decryptStr(val);
        return encryptValue;
    }

    /**
     * 字符串按长度进行分词
     *
     * @param phone
     * @return
     */
    public static String phoneKeywords(String phone) {
        String keywords = keywords(phone, 4);
        //System.out.println("加密后长度 " + keywords.length());
        return keywords;
    }

    /**
     * 分词组合加密
     *
     * @param word
     * @param len
     * @return
     */
    private static String keywords(String word, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            int start = i;
            int end = i + len;
            String sub1 = word.substring(start, end);
            //System.out.println("每次截取数据 " + sub1);
            sb.append(SymmetricCryptoUtils.encrypt(sub1));
            if (end == word.length()) {
                break;
            }
        }
        return sb.toString();
    }

}
