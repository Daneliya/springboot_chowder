package com.xxl.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xxl
 * @date 2022/7/28 23:37
 */
@RestController
public class ListenerController {

    @RequestMapping("/test")
    public Object test(String name) {
        return "hello " + name;
    }

}
