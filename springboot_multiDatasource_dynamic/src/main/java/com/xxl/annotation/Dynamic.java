package com.xxl.annotation;

import java.lang.annotation.*;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/27 9:10
 * @Version: 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Dynamic {
    String value() default "";
}
