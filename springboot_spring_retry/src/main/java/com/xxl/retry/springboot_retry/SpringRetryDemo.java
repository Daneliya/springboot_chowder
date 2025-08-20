package com.xxl.retry.springboot_retry;

import com.xxl.retry.spring_retry.RetryDemoTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * spring_retry 注解重试方法
 *
 * @author xxl
 * @date 2022/12/26 23:12
 */
@Service
@Slf4j
public class SpringRetryDemo {

    /**
     * 重试所调用方法
     *
     * @param param
     * @return
     */
    @Retryable(value = {RemoteAccessException.class, NullPointerException.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L, multiplier = 2))
    public boolean call(String param) {
        return RetryDemoTask.retryTask(param);
    }

    /**
     * 达到最大重试次数,或抛出了一个没有指定进行重试的异常
     * recover 机制
     *
     * @param e 异常
     */
    @Recover
    public boolean recover(Exception e, String param) {
        log.error("达到最大重试次数,或抛出了一个没有指定进行重试的异常:", e);
        return false;
    }

}