package com.xxl.shardingjdbc.controller;

import com.xxl.shardingjdbc.entity.User;
import com.xxl.shardingjdbc.mapper.UserMapper;
import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/10 17:25
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/save")
    public String insert() {
        User user = new User();
        user.setNickname("zhangsan" + new Random().nextInt());
        user.setPassword("1234567");
        user.setSex(1);
        user.setBirthday("1988-12-03");
        userMapper.addUser(user);
        return "success";
    }

    @GetMapping("/listuser")
    public List<User> listuser() {
        return userMapper.findUsers();
    }

    /**
     * 不分库只分表添加数据测试
     *
     * @return
     */
    @GetMapping("/onlyTableSave")
    public String onlyTableSave() {
        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.setNickname("name_" + i);
            user.setPassword("1234567");
            user.setSex(i % 2 == 0 ? 1 : 2);
            user.setDb(i < (20 / 2) ? 1 : 2);
            user.setBirthday("1988-12-03");
            userMapper.addUser(user);
        }
        return "success";
    }

}
