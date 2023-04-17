package com.xxl.config2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 自定义注解
 * 切换至主数据源-自定义注解
 * 这个仅为了方便使用，用SwitchRds注解指定为默认数据源也可以实现
 * @Author: xxl
 * @Date: 2023/02/13 9:26
 * @Version: 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SwitchMasterRds {
}