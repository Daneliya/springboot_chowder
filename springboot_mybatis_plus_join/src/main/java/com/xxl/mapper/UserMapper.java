package com.xxl.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xxl
 * @date 2023/4/18 23:35
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
