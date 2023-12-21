package com.xxl.mongodb.controller;

import com.xxl.mongodb.result.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxl
 * @date 2023/12/21 0:46
 */
@RestController
public class MongoTemplateController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping(value = "/test001")
    public void test001() {
        List<SysUser> sysUserList = new ArrayList<>();
        SysUser user = new SysUser();
        user.setUserName("admin");
        user.setAddress("地址");
        sysUserList.add(user);

        //  保存对象到mongodb
        mongoTemplate.save(user);
//        mongoTemplate.insert(user);
    }


}
