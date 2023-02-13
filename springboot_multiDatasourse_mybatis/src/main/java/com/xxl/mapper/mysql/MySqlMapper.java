package com.xxl.mapper.mysql;

import com.xxl.vo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/13 10:53
 * @Version: 1.0
 */
@Mapper
public interface MySqlMapper {

    List<User> getUser();

}
