package com.xxl.caffeine.service;

import com.xxl.caffeine.pojo.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 用户服务层
 *
 * @author xxl
 * @date 2023/6/6 0:16
 */
@Service
public class UserService {

    @Cacheable(value = "FIVE", key = "#id", sync = true)
    public User getById(Integer id) {
        System.out.println("操作数据库，进行通过ID查询，ID: " + id);
        return new User(id, "admin", "123", 18);
    }

    @CacheEvict(value = "FIVE", key = "#id")
    public void delete(Integer id) {
        System.out.println("删除key为[" + id + "]的缓存");
    }

}