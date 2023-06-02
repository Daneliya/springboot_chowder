package com.xxl.encrypt_hutool.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxl.encrypt_hutool.entity.Person;
import com.xxl.encrypt_hutool.mapper.PersonMapper;
import com.xxl.encrypt_hutool.service.PersonService;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/05/31 17:51
 * @Version: 1.0
 */
@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements PersonService {
}
