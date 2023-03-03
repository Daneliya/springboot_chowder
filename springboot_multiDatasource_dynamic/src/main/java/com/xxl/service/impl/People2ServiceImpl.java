package com.xxl.service.impl;

import com.xxl.annotation.Dynamic;
import com.xxl.mapper.PeopleMapper;
import com.xxl.pojo.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/03/03 14:07
 * @Version: 1.0
 */
@Service
public class People2ServiceImpl {

    @Autowired
    private PeopleMapper peopleMapper;

    @Dynamic("slave")
    public boolean sSave(People people) {
        People slavePeople = new People();
        slavePeople.setName("slave-" + people.getName());
        peopleMapper.save(slavePeople);
        return true;
    }

}
