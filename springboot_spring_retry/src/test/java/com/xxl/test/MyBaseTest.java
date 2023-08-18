package com.xxl.test;

import com.xxl.ReplyApplication;
import com.xxl.springboot_retry.SpringRetryDemo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xxl
 * @date 2022/12/26 22:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReplyApplication.class)
@Slf4j
public class MyBaseTest {


    @Before
    public void init() {
        log.info("----------------测试开始---------------");
    }

    @After
    public void after() {
        log.info("----------------测试结束---------------");
    }

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
