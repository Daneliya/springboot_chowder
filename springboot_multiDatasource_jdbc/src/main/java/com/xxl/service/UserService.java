package com.xxl.service;

import com.xxl.dao.UserDao;
import com.xxl.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: UserService类
 * @Author: xxl
 * @Date: 2023/02/20 1:53
 * @Version: 1.0
 */
@Service
public class UserService {

    @Resource
    private UserDao userDao;

    public List<User> getMysql() {
        return userDao.getMysql();
    }

    public List<User> getOracle() {
        return userDao.getOracle();
    }

}