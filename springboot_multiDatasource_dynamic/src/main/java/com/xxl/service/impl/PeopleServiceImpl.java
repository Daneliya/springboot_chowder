package com.xxl.service.impl;

import com.xxl.annotation.Dynamic;
import com.xxl.mapper.PeopleMapper;
import com.xxl.service.People2Service;
import com.xxl.service.PeopleService;
import com.xxl.pojo.People;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/15 23:32
 * @Version: 1.0
 */
@Service
public class PeopleServiceImpl implements PeopleService {

    private final PeopleMapper peopleMapper;

    @Autowired
    private People2Service people2Service;

    @Autowired
    public PeopleServiceImpl(PeopleMapper peopleMapper) {
        this.peopleMapper = peopleMapper;
    }

    /**
     * 说明: 不能和原生 Spring 事务混合,不使用 @DSTransactional 注解无法开启事务,即事务不会生效
     */

    // 从库,如果按照下划线命名方式配置多个,可以指定前缀即可.如slave_1、slave_2、slave3...,只需要设置salve即可,默认使用负载均衡算法
    @Dynamic("master")
    @Override
    public List<People> list() {
        return peopleMapper.list();
    }

    @Dynamic("master")
    @Override
    public boolean mSave(People people) {
        People masterPeople = new People();
        masterPeople.setName("master-" + people.getName());
        peopleMapper.save(masterPeople);
        return true;
    }

    @Dynamic("slave")
    @Override
    public boolean sSave(People people) {
        People slavePeople = new People();
        slavePeople.setName("slave-" + people.getName());
        peopleMapper.save(slavePeople);
        return true;
    }

    //@DSTransactional
    @Override
    public boolean save(People people) {
        PeopleService peopleService = (PeopleService) AopContext.currentProxy();
        peopleService.mSave(people);
        peopleService.sSave(people);
        sSave(people);
        people2Service.sSave(people);
        // 模拟事务回滚
//        int a = 1 / 0;
        return true;
    }


}
