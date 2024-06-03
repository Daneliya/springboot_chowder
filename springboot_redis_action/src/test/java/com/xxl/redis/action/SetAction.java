package com.xxl.redis.action;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * @author xxl
 * @date 2024/6/4 0:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SetAction {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void setAction1() {
        BoundSetOperations operations = redisTemplate.boundSetOps("user:tags:1");
        operations.add("car", "student", "rich", "guangdong", "dog", "rich");

        Set<String> set1 = operations.members();
        System.out.println(set1);
        operations.remove("dog");

        Set<String> set2 = operations.members();
        System.out.println(set2);
    }

    @Test
    public void setAction2() {
        // 用户A
        BoundSetOperations operationsA = redisTemplate.boundSetOps("user:a");
        // 关注了用户A的用户
        operationsA.add("A", "B", "C", "D");
        System.out.println("老A的粉丝" + operationsA.members());

        BoundSetOperations operationsB = redisTemplate.boundSetOps("user:b");
        operationsB.add("A", "B", "F", "G", "O", "W");
        System.out.println("老B的粉丝" + operationsB.members());

        // 差集
        Set setA = operationsA.diff("user:b");
        System.out.println("A的专属用户" + setA);

        // 交集
        Set set = operationsA.intersect("user:b");
        System.out.println("共同好友" + set);

        // 并集
        Set union = operationsA.union("user:b");
        System.out.println("全部好友" + union);

        // 是否存在集合中
        Boolean c = operationsA.isMember("C");
        System.out.println("用户c是不是老王的粉丝" + c);
    }
}
