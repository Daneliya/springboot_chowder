package com.xxl.encrypt_hutool.anno.aspect;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.xxl.encrypt_hutool.anno.DecryptField;
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
 * @Date: 2023/06/01 11:37
 * @Version: 1.0
 */
@Component
@Aspect
@Slf4j
public class DecryptAop {

    /**
     * 定义需要解密的切入点
     */
    @Pointcut(value = "@annotation(com.xxl.encrypt_hutool.anno.NeedDecrypt)")
    public void pointcut() {
    }

    /**
     * 命中的切入点时的环绕通知
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("//环绕通知 start");
        //执行目标方法
        Object result = proceedingJoinPoint.proceed();
        //判断目标方法的返回值类型
        if (result instanceof List) {
            for (Object tmp : ((List) result)) {
                //数据脱敏处理逻辑
                this.deepProcess(tmp);
            }
        } else {
            this.deepProcess(result);
        }
        log.info("//环绕通知 end");
        return result;
    }

    public void deepProcess(Object obj) throws IllegalAccessException {
        if (obj != null) {
            //取出输出对象的所有字段属性，并遍历
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                //判断字段属性上是否标记DecryptField注解
                if (declaredField.isAnnotationPresent(DecryptField.class)) {
                    //如果判断结果为真，则取出字段属性数据进行解密处理
                    declaredField.setAccessible(true);
                    Object valObj = declaredField.get(obj);
                    if (valObj != null) {
                        String value = valObj.toString();
                        //加密数据的解密处理
                        value = this.decrypt(value);
                        DecryptField annotation = declaredField.getAnnotation(DecryptField.class);
                        boolean open = annotation.open();
                        //数据解密后，判断是否开启了数据脱敏处理；
                        if (open) {
                            //如果开启，则开始进行数据脱敏处理
                            int start = annotation.start();
                            int offset = annotation.offset();
                            value = this.secret(value, start, offset);
                        }
                        //把解密后的数据重新赋值
                        declaredField.set(obj, value);
                    }
                }
            }
        }
    }

    private String decrypt(String value) {
        //这里特别注意一下，对称加密是根据密钥进行加密和解密的，加密和解密的密钥是相同的，一旦泄漏，就无秘密可言，
        //“fanfu-csdn”就是我自定义的密钥，这里仅作演示使用，实际业务中，这个密钥要以安全的方式存储；
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.DES.getValue(), "fanfu-csdn".getBytes()).getEncoded();
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.DES, key);
        String decryptStr = aes.decryptStr(value);
        return decryptStr;
    }

    private String secret(String value, Integer start, Integer limit) {
        //如果有特殊需要，还可以定义其他用于代替敏感数据的字符，一般情况下，使用的是“*”
        char[] chars = value.toCharArray();
        try {
            for (int i = start; i < start + limit; i++) {
                // 长度大于下标，防止数据越界
                if (chars.length > i) {
                    chars[i] = '*';
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return String.valueOf(chars);
    }
}
