package com.xxl.elk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xxl
 * @date 2023/11/16
 */
@RequestMapping("/api")
@RestController
public class ElkController {

    @GetMapping
    public String addLog(@RequestParam(value = "param1", required = false) String param1) {
        return "你好，这段话将被日志记录";
    }


}

