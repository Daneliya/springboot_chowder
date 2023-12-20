package com.xxl.mongodb;

import com.xxl.mongodb.result.SysUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxl
 * @date 2023/12/21 0:46
 */
@SpringBootTest
public class MongoTemplateTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void test001() {
        List<SysUser> sysUserList = new ArrayList<>();
        SysUser user = new SysUser();
        user.setUserName("admin");
        user.setAddress("地址");
        sysUserList.add(user);

        //  保存对象到mongodb
        mongoTemplate.save(user);
        mongoTemplate.insert(user);
    }


}
