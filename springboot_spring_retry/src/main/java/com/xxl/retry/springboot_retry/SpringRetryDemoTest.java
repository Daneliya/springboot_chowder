package com.xxl.retry.springboot_retry;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * spring_retry 注解测试类
 *
 * @author xxl
 * @date 2022/12/26 23:11
 */
@Component
@Slf4j
public class SpringRetryDemoTest {

    @Autowired
    private SpringRetryDemo springRetryDemo;

    /**
     * 只要在需要重试的方法上加@Retryable，在重试失败的回调方法上加@Recover
     */
    @Test
    public void retry() {
        boolean abc = springRetryDemo.call("abc");
        log.info("--结果是:{}--", abc);
    }
}