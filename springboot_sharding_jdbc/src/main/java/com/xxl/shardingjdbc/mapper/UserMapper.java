package com.xxl.shardingjdbc.mapper;

import com.xxl.shardingjdbc.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/10 17:24
 * @Version: 1.0
 */
@Mapper
public interface UserMapper {

    /**
     * @author xxl
     * @description 保存用户
     * @params [user]
     * @date 2023/02/10 17:24
     */
    @Insert("insert into user(nickname,password,sex,birthday,db) values(#{nickname},#{password},#{sex},#{birthday},#{db})")
    void addUser(User user);

    /**
     * @author xxl
     * @description 保存用户
     * @params [user]
     * @date 2023/02/10 17:24
     */
    @Select("select * from user")
    List<User> findUsers();
}