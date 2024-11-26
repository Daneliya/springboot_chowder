package com.xxl.string.trimSpaces;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * 空字符串过滤处理
 *
 * @Author: xxl
 * @Date: 2024/11/26 13:15
 */
@Aspect
@Component
public class TrimSpacesAspect {

    // 配置织入点
    @Pointcut("@annotation(com.xxl.string.trimSpaces.TrimSpaces)")
    public void logPointCut() {
    }

    //    @Before("execution(* com.xxl.string..) && args(entity)")
//    @Before("@annotation(com.xxl.string.trimSpaces.TrimSpaces)")
//    @Around("execution(* *.*(..)) && @args(com.xxl.string.trimSpaces.TrimSpaces)")
//    public void trimSpaces(JoinPoint joinPoint, Object entity) throws IllegalAccessException {
//        Class<?> clazz = entity.getClass();
//        Field[] fields = clazz.getDeclaredFields();
//
//        for (Field field : fields) {
//            if (field.isAnnotationPresent(TrimSpaces.class)) {
//                field.setAccessible(true);
//                Object value = field.get(entity);
//                if (value instanceof String) {
//                    String trimmedValue = ((String) value).replaceAll("\\s+", "");
//                    field.set(entity, trimmedValue);
//                }
//            }
//        }
//    }

    @Around("execution(* *.*(..)) && @args(com.xxl.string.trimSpaces.TrimSpaces)")
    public Object trimStringFields(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg == null) continue;

            Field[] fields = arg.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getType() == String.class && field.isAnnotationPresent(TrimSpaces.class)) {
                    String value = (String) field.get(arg);
                    if (value != null) {
                        field.set(arg, value.trim());
                    }
                }
            }
        }

        return joinPoint.proceed();
    }
}