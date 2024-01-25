package com.xxl.elk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xxl
 * @date 2023/11/16
 */

@RequestMapping("/api")
@RestController
public class ElkController {

    /**
     * 获取日志输出对象
     */
    private static final Logger log = LoggerFactory.getLogger(ElkController.class);

    /**
     * 测试输出log的访问方法
     */
    @GetMapping("/testLog")
    public String testLog() {
        log.info("{日志},{}", "测试输出一个日志");
        log.error("{日志},{}", "测试输出一个错误日志");
        return "success";
    }
}

