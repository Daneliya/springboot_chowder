package com.xxl.poi.Excel;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;

import java.lang.reflect.Array;

/**
 * 重写 convert 方法
 *
 * @Author: xxl
 * @Date: 2021/09/02 9:13
 */
public class ExtConvertUtilsBean extends ConvertUtilsBean {

    /**
     * 注册类型转换器，自定义方法
     * 转换的内部实现方法需要重写
     * @param value
     * @return
     */
    @Override
    public String convert(Object value) {
        if (value == null) {
            return null;
        } else if (value.getClass().isArray()) {
            if (Array.getLength(value) < 1) {
                return (null);
            }
            value = Array.get(value, 0);
            if (value == null) {
                return null;
            } else {
                Converter converter = lookup(String.class);
                return (converter.convert(String.class, value));
            }
        } else {
            Converter converter = null;
            if (value instanceof java.util.Date) {
                converter = lookup(java.util.Date.class);
            } else {
                converter = lookup(String.class);
            }
            return (converter.convert(String.class, value));
        }
    }
}
