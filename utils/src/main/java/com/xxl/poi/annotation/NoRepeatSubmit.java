package com.xxl.poi.annotation;

import java.lang.annotation.*;

/**
 * 防重复注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoRepeatSubmit {
    /**
     * 默认时间  默认1秒钟
     *
     * @return
     */
    int lockTime() default 1000;
}
