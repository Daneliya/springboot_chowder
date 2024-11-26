package com.xxl.trimSpaces;

import com.xxl.trimSpaces.annotation.TrimSpaces;
import org.aspectj.lang.JoinPoint;
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
    @Pointcut("@annotation(com.xxl.trimSpaces.annotation.TrimSpaces)")
    public void logPointCut() {
    }

    @Around(value = "logPointCut()")
    public Object trimSpaces(ProceedingJoinPoint joinPoint) throws Throwable {
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
        // 执行原方法并返回结果
        return joinPoint.proceed();
    }

//    @Around("execution(* com.xxl.controller.DemoController2.test(..))")
////    @Around(value = "logPointCut()")
////    @Around(value = "@annotation(TrimSpaces)")
//    public Object trimSpacesParam(ProceedingJoinPoint joinPoint) throws Throwable {
//        // 获取方法参数
//        Object[] args = joinPoint.getArgs();
//
//        for (Object arg : args) {
//            if (arg == null) continue;
//
//            Field[] fields = arg.getClass().getDeclaredFields();
//            for (Field field : fields) {
//                field.setAccessible(true);
//                if (field.getType() == String.class && field.isAnnotationPresent(TrimSpaces.class)) {
//                    String value = (String) field.get(arg);
//                    if (value != null) {
//                        field.set(arg, value.trim());
//                    }
//                }
//            }
//        }
//        return joinPoint.proceed();
//    }
}