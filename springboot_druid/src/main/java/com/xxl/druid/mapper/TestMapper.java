package com.xxl.druid.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: xxl
 * @Date: 2024/11/18 16:23
 */
@Mapper    //必须要声明的，不然系统不知道这个类是作为底层交互的
public interface TestMapper {

    @Select("select * from sys_user where tenant_id = #{tenantId}")
    List<Object> queryUser(Integer tenantId);

}
