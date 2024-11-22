package com.xxl.aynsc.controller;

import com.xxl.aynsc.service.AynscService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xxl
 * @Date: 2024/11/22 15:17
 */
@RestController
@RequestMapping("/api/aynsc")
public class AynscController {

    @Autowired
    private AynscService aynscService;

    @GetMapping("/testAynsc")
    public String testAynsc() {
        for (int i = 1; i <= 100; i++) {
            aynscService.sendAynscMessage(i);
        }
        return "执行成功";
    }
}
