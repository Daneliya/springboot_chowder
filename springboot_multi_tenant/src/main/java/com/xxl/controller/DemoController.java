package com.xxl.controller;

import com.xxl.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xxl
 * @date 2022/5/5 23:30
 */
@RestController
public class DemoController {

    @Resource
    private UserService userService;

    @RequestMapping("/get")
    public Object get() {
        return userService.get();
    }

}
