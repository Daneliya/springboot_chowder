package com.xxl.caffeine.controller;

import com.xxl.caffeine.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户控制层
 *
 * @author xxl
 * @date 2023/6/6 0:17
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/getById")
    public Object getById(Integer id) {
        return userService.getById(id);
    }

    @RequestMapping("/delete")
    public Object delete(Integer id) {
        userService.delete(id);
        return "删除成功！";
    }

}