package com.xxl.trimSpaces.annotation;

import java.lang.annotation.*;

/**
 * 过滤空字符串
 *
 * @Author: xxl
 * @Date: 2024/11/26 13:14
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TrimSpaces {
    // 编写一个java注解，过滤实体中添加注解的字段数据有空白字符的及 Aspect  @Around(value = @annotation)
//    String message() default "字段不能包含空白字符";
}
