package com.xxl.canal.service.impl;

import com.xxl.canal.entity.User;
import com.xxl.canal.service.UserService;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

/**
 * @author xxl
 * @date 2023/6/5 22:32
 */
@CanalTable("user")
@Component
public class UserServiceImpl implements EntryHandler<User>, UserService {

    @Override
    public void insert(User item) {
        System.out.println("insert," + item);
    }
    @Override
    public void update(User before, User after) {
        System.out.println("update before," + before);
        System.out.println("update after," + after);
    }
    @Override
    public void delete(User item) {
        System.out.println("delete," + item);
    }

}
