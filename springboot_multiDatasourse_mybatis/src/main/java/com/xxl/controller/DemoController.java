package com.xxl.controller;

import com.xxl.service.UserService;
import com.xxl.vo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/13 10:58
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