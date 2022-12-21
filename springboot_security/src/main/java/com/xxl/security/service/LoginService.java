package com.xxl.security.service;

import com.xxl.security.entity.User;
import com.xxl.security.result.ResponseResult;

/**
 * @author xxl
 * @date 2022/12/18 0:56
 */
public interface LoginService {

    /**
     * 登录
     *
     * @param user
     * @return
     */
    ResponseResult login(User user);

    /**
     * 退出登陆
     *
     * @return
     */
    ResponseResult logout();
}
