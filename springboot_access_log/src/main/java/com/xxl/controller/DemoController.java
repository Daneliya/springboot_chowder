package com.xxl.controller;

import com.xxl.vo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 *
 * @author xxl
 * @date 2022/5/8 18:57
 */
@RestController
public class DemoController {

    @RequestMapping("/test")
    public User test(User user) {
        user.setAge(18);
        return user;
    }

    @RequestMapping("/test02")
    public User test02(User user) {
        user.setAge(20);
        return user;
    }
}
