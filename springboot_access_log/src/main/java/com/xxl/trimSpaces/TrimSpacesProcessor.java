package com.xxl.trimSpaces;

import com.xxl.trimSpaces.annotation.TrimSpaces;

import java.lang.reflect.Field;

/**
 * 空字符串过滤工具
 *
 * @Author: xxl
 * @Date: 2024/11/26 14:42
 */
public class TrimSpacesProcessor {

    public static <T> T process(T object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(TrimSpaces.class)) {
                field.setAccessible(true);
                Object value = field.get(object);
                if (value instanceof String) {
                    String trimmedValue = ((String) value).replaceAll("\\s+", "");
                    field.set(object, trimmedValue);
                }
            }
        }

        return object;
    }

}
