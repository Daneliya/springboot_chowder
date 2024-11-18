package com.xxl.druid.controller;

import com.xxl.druid.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: xxl
 * @Date: 2024/11/18 16:18
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public List<Object> queryUser() {
        Integer tenantId = 332;
        return testService.queryUser(tenantId);
    }
}
