package com.xxl.mybatis_plus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxl.mybatis_plus.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xxl
 * @date 2023/4/19 0:22
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
