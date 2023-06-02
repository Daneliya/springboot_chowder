package com.xxl.encrypt_hutool.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 解密字段
 * @Author: xxl
 * @Date: 2023/06/01 11:27
 * @Version: 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DecryptField {

    /**
     * 是否开启敏感数据脱敏处理，默认不开启
     *
     * @return
     */
    boolean open() default false;

    /**
     * 脱敏开始位置索引
     *
     * @return
     */
    int start() default 0;

    /**
     * 脱敏从开始位置向后偏移量
     *
     * @return
     */
    int offset() default 6;
}
