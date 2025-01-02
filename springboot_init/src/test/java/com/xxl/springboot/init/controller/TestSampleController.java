package com.xxl.springboot.init.controller;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author xxl
 * @date 2024/12/29 0:22
 */
@SpringBootTest(classes = SampleController.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestSampleController {

    @Autowired
    private SampleController sampleController;

    @Test
    public void testHome() {
        TestCase.assertEquals(this.sampleController.home(), "www.xxl.cnn");
    }
}
