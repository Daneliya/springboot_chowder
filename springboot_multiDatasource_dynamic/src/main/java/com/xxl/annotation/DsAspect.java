package com.xxl.annotation;

import com.xxl.config.DynamicDataSourceContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/27 9:10
 * @Version: 1.0
 */
@Order(value = -1)
@Aspect
@Component
public class DsAspect {

//    @Resource
//    private DynamicDataSourceContext dynamicDataSourceContext;

    // 拦截类上有DS注解的方法调用
//    @Around("@within(Dynamic)")
//    public Object dsAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        Dynamic ds = (Dynamic) proceedingJoinPoint.getSignature().getDeclaringType().getAnnotation(DS.class);
//        System.out.println("注解拦截 " + ds.value());
//        try {
//            // 写入线程上下文，应该用哪个DB
//            DynamicDataSourceContext.setDataSource(ds == null ? null : ds.value());
//            return proceedingJoinPoint.proceed();
//        } finally {
//            // 清空上下文信息
//            DynamicDataSourceContext.clearDataSource();
//        }
//    }

    @Pointcut("within(com.xxl.service.*)||@annotation(Dynamic)")
//    @Pointcut("within(com.xxl.service.impl.*)||@annotation(Dynamic)")
    public void ds() {
    }

    @Before(value = "ds()")
    public void before(JoinPoint joinPoint) {
        System.out.println("注解拦截 start" + joinPoint);
        // 获得注解
        Dynamic controllerDynamic = getAnnotationLog(joinPoint);
        if (controllerDynamic == null) {
            System.out.println("未查询到注解信息");
        }
        System.out.println(controllerDynamic);
        try {
            // 写入线程上下文，应该用哪个DB
//            dynamicDataSourceContext.setDataSource(controllerDynamic.value() == null ? null : controllerDynamic.value());
            DynamicDataSourceContext.setDataSource(controllerDynamic.value());
//            DynamicDataSourceContext.name.set(controllerDynamic.value());
        } finally {
            // 清空上下文信息
//            DynamicDataSourceContext.clearDataSource();
//            DynamicDataSourceContext.name.remove();
        }
//        Class cls = joinPoint.getSignature().getDeclaringType();
//        // 如果类上面有就使用 类上面的
//        Dynamic ds = AnnotationUtils.findAnnotation(cls, Dynamic.class);
//        System.out.println(ds);
//        if (ds != null && !"".equals(ds)) {
//            DynamicDataSourceContextHolder.push(ds.value());
//            System.out.println(cls.getSimpleName() + "类上面的 动态 数据源 :" + ds.value());
//        }
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//        DS annotation = AnnotationUtils.findAnnotation(method, DS.class);
//        if (annotation != null && !"".equals(annotation)) {
//            DynamicDataSourceContextHolder.push(ds.value());
//            System.out.println(method.getName() + "方法上面的动态数据源 :" + ds.value());
//        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Dynamic getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Dynamic.class);
        }
        return null;
    }

    @After("@annotation(Dynamic)")
    public void dynamicAfter() {
        // 清空上下文信息
        DynamicDataSourceContext.clearDataSource();

    }
}