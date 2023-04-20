package com.xxl.mybatis_plus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxl.mybatis_plus.entity.User;
import com.xxl.mybatis_plus.mapper.UserMapper;
import com.xxl.mybatis_plus.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Description: (User)表服务实现类
 * @Author: xxl
 * @Date: 2023/04/20 9:28
 * @Version: 1.0
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}