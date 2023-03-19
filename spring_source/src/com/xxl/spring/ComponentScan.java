package com.xxl.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 *
 * @author xxl
 * @date 2023/3/19 15:38
 */
@Retention(RetentionPolicy.RUNTIME) // 生效时间
@Target(ElementType.TYPE) // 只能写在类上
public @interface ComponentScan {

    String value() default "";
}
