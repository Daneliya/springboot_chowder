package com.xxl.encrypt_hutool.anno.aspect;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.xxl.encrypt_hutool.anno.EncryptField;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/06/01 11:35
 * @Version: 1.0
 */
@Component
@Aspect
@Slf4j
public class EncryptAop {

    /**
     * 定义加密切入点
     */
    @Pointcut(value = "@annotation(com.xxl.encrypt_hutool.anno.NeedEncrypt)")
    public void pointcut() {
    }

    /**
     * 命中加密切入点的环绕通知
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("//环绕通知 start");
        //获取命中目标方法的入参数
        Object[] args = proceedingJoinPoint.getArgs();
        if (args.length > 0) {
            for (Object arg : args) {
                //按参数的类型进行判断，如果业务中还有其他的类型，可酌情增加
                if (arg != null) {
                    if (arg instanceof List) {
                        for (Object tmp : ((List) arg)) {
                            //加密处理
                            this.deepProcess(tmp);
                        }
                    } else {
                        this.deepProcess(arg);
                    }
                }
            }
        }
        //对敏感数据加密后执行目标方法
        Object result = proceedingJoinPoint.proceed();
        log.info("//环绕通知 end");
        return result;
    }

    public void deepProcess(Object obj) throws IllegalAccessException {
        if (obj != null) {
            //获取对象的所有字段属性并遍历
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                //判断字段属性上是否标记了@EncryptField注解
                if (declaredField.isAnnotationPresent(EncryptField.class)) {
                    //如果判断结果为真，则取出字段属性值，进行加密、重新赋值
                    declaredField.setAccessible(true);
                    Object valObj = declaredField.get(obj);
                    if (valObj != null) {
                        String value = valObj.toString();
                        //开始敏感字段属性值加密
                        String decrypt = this.encrypt(value);
                        //把加密后的字段属性值重新赋值
                        declaredField.set(obj, decrypt);
                    }
                }
            }
        }
    }

    private String encrypt(String value) {
        //这里特别注意一下，对称加密是根据密钥进行加密和解密的，加密和解密的密钥是相同的，一旦泄漏，就无秘密可言，
        //“fanfu-csdn”就是我自定义的密钥，这里仅作演示使用，实际业务中，这个密钥要以安全的方式存储；
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.DES.getValue(), "fanfu-csdn".getBytes()).getEncoded();
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.DES, key);
        String encryptValue = aes.encryptBase64(value);
        return encryptValue;
    }

}