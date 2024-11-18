package com.xxl.druid.service.impl;

import com.xxl.druid.mapper.TestMapper;
import com.xxl.druid.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: xxl
 * @Date: 2024/11/18 16:26
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public List<Object> queryUser(Integer tenantId) {
        return testMapper.queryUser(tenantId);
    }
}
