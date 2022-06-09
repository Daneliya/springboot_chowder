package com.xxl;

import com.xxl.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xxl
 * @Date: 2022/05/04 23:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() {
        //赋值方法
//        redisTemplate.opsForValue().set("username1", "wangxinli");
//        redisTemplate.opsForValue().set("username2", "yanxiaotang");
//        redisTemplate.opsForValue().set("username3", "zhangguoshun");

        //向redis里存入数据和设置缓存时间
        //redisTemplate.opsForValue().set("baike", "100", 60 * 10, TimeUnit.SECONDS);
        User user = new User();
        user.setName("zhangsan");
        user.setAge(18);
        User user2 = new User();
        user2.setName("lisi");
        user2.setAge(19);
        redisTemplate.opsForList().remove("SIGN_INFO:" + "user_list", 0, user);
        redisTemplate.opsForList().rightPush("SIGN_INFO:" + "user_list", user);
        redisTemplate.opsForList().rightPush("SIGN_INFO:" + "user_list", user2);
        //push时value传的是什么类型，range方法后接受的list<>中就传什么类型
//        List<String> lists = redisTemplate.opsForList().range("user_list", 0, -1);
//        for (String str : lists) {
//            System.out.println(str);
//        }
        List<User> lists = redisTemplate.opsForList().range("SIGN_INFO:" + "user_list", 0, -1);
        for (User str : lists) {
            System.out.println(str.toString());
        }
        //设置过期时间
        redisTemplate.expire("SIGN_INFO:" + "user_list", 100000, TimeUnit.MILLISECONDS);
    }


    @Test
    public void set() {
        User user = new User();
        user.setName("wangwu");
        user.setAge(22);
        //1、通过redisTemplate设置值
        redisTemplate.boundSetOps("setKey").add(user);
//        Set<User> set1 = redisTemplate.boundSetOps("setKey").members();
//        System.out.println(set1.toString());
    }

    @Test
    public void zSet() {
        User user = new User();
        user.setName("wangwu");
        user.setAge(22);
        User user2 = new User();
        user2.setName("lisi");
        user2.setAge(22);
        Long zSetKey = redisTemplate.boundZSetOps("zSetKey").size();
        redisTemplate.boundZSetOps("zSetKey").add(user2, zSetKey + 1);
    }

    @Test
    public void getZSet() {
        Set<User> signInUserZSet = redisTemplate.boundZSetOps("zSetKey").range(0, -1);
        System.out.println(signInUserZSet);
    }

    @Test
    public void test001() {
        Object url = redisTemplate.opsForValue().get("image_token:" + "16545e7c96c744329556a2ff9713304f");
//        Object url = redisTemplate.boundZSetOps("image_token:" + "16545e7c96c744329556a2ff9713304f");
        System.out.println(url);
        if (url == null) {
            Map<String, Object> objectMap = new HashMap<>();
            //5001 二维码失效
            objectMap.put("code", 5001);
            objectMap.put("note", "二维码签到失败！请重新扫码！");
            System.out.println(objectMap);
        }
    }
}
