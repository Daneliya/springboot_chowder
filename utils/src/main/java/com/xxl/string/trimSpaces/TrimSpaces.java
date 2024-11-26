package com.xxl.string.trimSpaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 过滤空字符串
 *
 * @Author: xxl
 * @Date: 2024/11/26 13:14
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TrimSpaces {
}
