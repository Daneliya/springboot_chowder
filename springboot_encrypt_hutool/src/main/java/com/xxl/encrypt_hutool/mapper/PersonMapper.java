package com.xxl.encrypt_hutool.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxl.encrypt_hutool.entity.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/06/01 8:57
 * @Version: 1.0
 */
@Mapper
public interface PersonMapper extends BaseMapper<Person> {

    List<Person> queryByPhoneEncrypt(Person person);
}
