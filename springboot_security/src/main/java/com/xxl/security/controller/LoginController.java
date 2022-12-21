package com.xxl.security.controller;

import com.xxl.security.entity.User;
import com.xxl.security.result.ResponseResult;
import com.xxl.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xxl
 * @date 2022/12/18 0:54
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @PreAuthorize("hasAuthority('test')")
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user) {
        return loginService.login(user);
    }

    /**
     * 退出登陆
     *
     * @return
     */
    @PostMapping("/user/logout")
    public ResponseResult logout() {
        return loginService.logout();
    }
}