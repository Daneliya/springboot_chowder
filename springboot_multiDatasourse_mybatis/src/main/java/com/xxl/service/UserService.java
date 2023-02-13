package com.xxl.service;

import com.xxl.mapper.mysql.MySqlMapper;
import com.xxl.mapper.oracle.OracleMapper;
import com.xxl.vo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/13 10:57
 * @Version: 1.0
 */
@Service
public class UserService {

    @Resource
    private MySqlMapper mySqlMapper;

    @Resource
    private OracleMapper oracleMapper;

    public List<User> getMysql() {
        return mySqlMapper.getUser();
    }

    public List<User> getOracle() {
        return oracleMapper.getUser();
    }

}