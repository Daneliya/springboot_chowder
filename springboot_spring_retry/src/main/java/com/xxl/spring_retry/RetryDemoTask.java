package com.xxl.spring_retry;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.remoting.RemoteAccessException;

/**
 * 普通使用方式
 *
 * @author xxl
 * @date 2022/12/26 22:53
 */
@Slf4j
public class RetryDemoTask {

    public static void main(String[] args) {
        retryTask("参数");
    }


    /**
     * 重试方法：采用一个随机整数，根据不同的条件返回不同的值，或者抛出异常
     *
     * @return
     */
    public static boolean retryTask(String param) {
        log.info("收到请求参数:{}", param);

        int i = RandomUtils.nextInt(0, 11);
        log.info("随机生成的数:{}", i);
        if (i == 0) {
            log.info("为0,抛出参数异常.");
            throw new IllegalArgumentException("参数异常");
        } else if (i == 1) {
            log.info("为1,返回true.");
            return true;
        } else if (i == 2) {
            log.info("为2,返回false.");
            return false;
        } else {
            //为其他
            log.info("大于2,抛出自定义异常.");
            throw new RemoteAccessException("大于2,抛出远程访问异常");
        }
    }

}