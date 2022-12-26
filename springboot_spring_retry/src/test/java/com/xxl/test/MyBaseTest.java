package com.xxl.test;

import com.xxl.ReplyApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
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
}
