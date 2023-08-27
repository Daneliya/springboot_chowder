package com.xxl.test;

import com.xxl.retry.ReplyApplication;
import com.xxl.retry.springboot_retry.SpringRetryDemo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xxl
 * @date 2022/12/26 22:47
 */
@EnableRetry
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

}
