package com.xxl.security;

import com.xxl.security.entity.User;
import com.xxl.security.mapper.MenuMapper;
import com.xxl.security.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * @author xxl
 * @date 2022/12/18 0:21
 */
@SpringBootTest
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 测试MP是否能正常使用
     */
    @Test
    public void testUserMapper() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    /**
     * 测试加密密码
     */
    @Test
    public void testPassword() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123");
        System.out.println(encode);
    }

    /**
     * 测试查询权限
     */
    @Test
    public void testPermission() {
        List<String> permissionKeyList = menuMapper.selectPermsByUserId(3L);
        System.out.println(permissionKeyList);
    }

}
