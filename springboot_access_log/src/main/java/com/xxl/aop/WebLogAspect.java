package com.xxl.aop;

import com.xxl.util.AsyncFactory;
import com.xxl.util.AsyncManager;
import com.xxl.util.ParametersUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 参数记录aop类
 *
 * @author xxl
 * @date 2022/5/8 19:55
 */
@Slf4j
@Aspect
@Component
public class WebLogAspect {

//    @Pointcut("execution(public * com.xxl.controller..*.*(..))")
    @Pointcut("execution(public * com.xxl.controller.DemoController.test02(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore() {
        // 获取请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        // 记录请求内容
        log.info("请求地址：" + request.getRequestURL().toString());
        log.info("请求方法：" + request.getMethod());
        log.info("请求者IP：" + request.getRemoteAddr());
        log.info("请求参数：" + ParametersUtils.getParameters(request));
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(null,null,null,null));
        log.info("返回结果：" + ret.toString());
    }

}
