package com.xxl.encrypt_hutool.controller;

/**
 * @Description: SM2 国米加密测试类
 * @Author: xxl
 * @Date: 2023/08/18 8:59
 * @Version: 1.0
 */
public class SmController {

    public static void main(String[] args) {
        String text = "我是一段测试aaaa";

        String publicKey = "xxx";
        String privateKey = "cc";
        //公钥加密
        text = encodeByPublicKey(publicKey, privateKey, text);
        System.out.println(text);
        //私钥解密
        text = decryptData(publicKey, privateKey, text);
        System.out.println(text);
    }

    public static String decryptData(String publicKey, String privateKey, String encryptData) {
        if (StrUtil.isBlank(encryptData)) {
            throw new MyException("解密串为空，解密失败");
        }
        SM2 sm2 = SmUtil.sm2(ECKeyUtil.toSm2PrivateParams(privateKey), ECKeyUtil.toSm2PublicParams(publicKey));
        // BC库解密时密文开头必须带04，如果没带04则需补齐
        if (!encryptData.startsWith("04")) {
            encryptData = "04".concat(encryptData);
        }
        byte[] decryptFromBcd = sm2.decryptFromBcd(encryptData, KeyType.PrivateKey);
        if (decryptFromBcd != null && decryptFromBcd.length > 0) {
            return StrUtil.utf8Str(decryptFromBcd);
        } else {
            throw new MyException("密文解密失败");
        }
    }

    /**
     * 加密
     *
     * @param publicKey
     * @param privateKey
     * @param data
     */
    public static String encodeByPublicKey(String publicKey, String privateKey, String data) {
        SM2 sm2 = SmUtil.sm2(ECKeyUtil.toSm2PrivateParams(privateKey), ECKeyUtil.toSm2PublicParams(publicKey));
        String encryptBcd = sm2.encryptBcd(data, KeyType.PublicKey);
        // 这里的处理前端也可以处理，这个就看怎么约定了，其实都无伤大雅
        if (StrUtil.isNotBlank(encryptBcd)) {
            // 生成的加密密文会带04，因为前端sm-crypto默认的是1-C1C3C2模式，这里需去除04才能正常解密
            if (encryptBcd.startsWith("04")) {
                encryptBcd = encryptBcd.substring(2);
            }
            // 前端解密时只能解纯小写形式的16进制数据，这里需要将所有大写字母转化为小写
            encryptBcd = encryptBcd.toLowerCase();
        }
        return encryptBcd;
    }

    /**
     * 获取公钥秘钥对
     */
    public static void getPrivateKeyPublicKey() {
        SM2 sm2 = SmUtil.sm2();
        // 私钥：这个保存好，切记不要泄漏，真的泄露了就重新生成一下
        byte[] privateKey = BCUtil.encodeECPrivateKey(sm2.getPrivateKey());
        // 公钥：这个是前后端加密用的，不压缩选择带04的，不带04到时候前端会报错
        byte[] publicKey = ((BCECPublicKey) sm2.getPublicKey()).getQ().getEncoded(false);
        Console.log("公钥：\n{}", HexUtil.encodeHexStr(publicKey));
        Console.log("私钥：\n{}", HexUtil.encodeHexStr(privateKey));
    }

}
