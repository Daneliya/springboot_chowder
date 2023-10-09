package com.xxl.repeat_submit.annotation;

import java.lang.annotation.*;

/**
 * 创建一个@SubmitToken注解，通过这个注解来进行方法代理拦截！
 *
 * @author xxl
 * @date 2022/11/17 23:37
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SubmitToken {

}