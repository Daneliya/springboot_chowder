package com.xxl.controller;

import com.xxl.entity.User;
import com.xxl.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 测试接口类
 * @Author: xxl
 * @Date: 2023/02/20 1:54
 * @Version: 1.0
 */
@RestController
public class DemoController {

    @Resource
    private UserService userService;

    @RequestMapping("/getMysql")
    public List<User> getMysql() {
        return userService.getMysql();
    }

    @RequestMapping("/getOracle")
    public List<User> getOracle() {
        return userService.getOracle();
    }

}