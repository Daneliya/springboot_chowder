package com.xxl.service;

import com.xxl.pojo.People;

import java.util.List;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/15 23:32
 * @Version: 1.0
 */
public interface PeopleService {

    // 从库,如果按照下划线命名方式配置多个,可以指定前缀即可.如slave_1、slave_2、slave3...,
    // 只需要设置salve即可,默认使用负载均衡算法
    List<People> list();

    boolean mSave(People people);

    boolean sSave(People people);

    boolean save(People people);
}
