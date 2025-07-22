package com.xxl.retry.spring_retry;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * spring_retry 测试类
 *
 * @author xxl
 * @date 2022/12/26 22:58
 */
@Slf4j
public class SpringRetryTemplateTest {

    /**
     * 重试间隔时间ms,默认1000ms
     */
    private final static long fixedPeriodTime = 1000L;

    /**
     * 最大重试次数,默认为3
     */
    private final static int maxRetryTimes = 3;

    /**
     * 表示哪些异常需要重试,key表示异常的字节码,value为true表示需要重试
     */
    private final Map<Class<? extends Throwable>, Boolean> exceptionMap = new HashMap<>();


    @Test
    public void test() {
        exceptionMap.put(RemoteAccessException.class, true);

        // 构建重试模板实例
        RetryTemplate retryTemplate = new RetryTemplate();

        // 设置重试回退操作策略，主要设置重试间隔时间
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(fixedPeriodTime);

        // 设置重试策略，主要设置重试次数
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(maxRetryTimes, exceptionMap);

        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        Boolean execute = retryTemplate.execute(
                //RetryCallback：重试回调逻辑实例，包装正常的功能操
                retryContext -> {
                    boolean b = RetryDemoTask.retryTask("abc");
                    log.info("调用的结果:{}", b);
                    return b;
                },
                retryContext -> {
                    //RecoveryCallback：整个执行操作结束的恢复操作实例
                    log.info("已达到最大重试次数或抛出了不重试的异常~~~");
                    return false;
                }
        );
        log.info("执行结果:{}", execute);
    }
}