package com.xxl.logback.controller;

import cn.hutool.core.util.RandomUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制层
 *
 * @author xxl
 * @date 2025/12/26 10:55
 */
@RestController
public class LogbackController {

    /**
     * 测试方法
     *
     * @return 返回结果
     */
    @GetMapping(value = "/test")
    public String test() {
        System.out.println(RandomUtil.randomString(10));
        return RandomUtil.randomString(10);
    }
}
