package com.xxl.mapper;

import com.xxl.pojo.People;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/15 23:29
 * @Version: 1.0
 */
@Mapper
public interface PeopleMapper {

    List<People> list();

    void save(People people);

}