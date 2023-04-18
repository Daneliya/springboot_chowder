package com.xxl.mybatis_plus;

import com.xxl.mybatis_plus.entity.User;
import com.xxl.mybatis_plus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author xxl
 * @date 2023/4/19 0:20
 */
@SpringBootTest
public class MybatisPlusApplicationTests {

    //继承了BaseMapper所有的方法，可以编写自己的扩展方法
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect(){
        System.out.println("--------selectAll method test-------");
        //查询全部用户，参数是一个Wrapper，条件构造器，先不使用为null
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

}
