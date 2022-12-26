package com.xxl.springboot_retry;

//import com.xxl.test.MyBaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xxl
 * @date 2022/12/26 23:11
 */
@Component
@Slf4j
public class SpringRetryDemoTest {

    @Autowired
    private SpringRetryDemo springRetryDemo;

    @Test
    public void retry() {
        boolean abc = springRetryDemo.call("abc");
        log.info("--结果是:{}--", abc);
    }

}