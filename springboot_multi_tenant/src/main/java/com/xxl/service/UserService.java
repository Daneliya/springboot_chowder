package com.xxl.service;

import com.xxl.dao.UserDao;
import com.xxl.vo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xxl
 * @date 2022/5/5 23:27
 */
@Service
public class UserService {

    @Resource
    private UserDao userDao;

    public List<User> get() {
        return userDao.get();
    }

}
