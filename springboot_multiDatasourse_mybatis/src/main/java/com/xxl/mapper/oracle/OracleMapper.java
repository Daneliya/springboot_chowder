package com.xxl.mapper.oracle;

import com.xxl.vo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/13 10:54
 * @Version: 1.0
 */
@Mapper
public interface OracleMapper {

    List<User> getUser();

}
