package com.xxl.repeat_submit.annotation;

import java.lang.annotation.*;

/**
 * @author xxl
 * @date 2022/11/17 23:50
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SubmitLimit {

    /**
     * 指定时间内不可重复提交（仅相对上一次发起请求时间差）,单位毫秒
     *
     * @return
     */
    int waitTime() default 1000;

    /**
     * 指定请求头部key，可以组合生成签名
     *
     * @return
     */
    String[] customerHeaders() default {};

    /**
     * 自定义重复提交提示语
     *
     * @return
     */
    String customerTipMsg() default "请勿重复请求！";
}