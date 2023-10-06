package com.xxl.redisson.controller;

import com.xxl.redisson.service.RedissonDelayQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 我们用Redisson实现一个优雅的延迟队列
 *
 * @author xxl
 * @date 2023/09/18
 */
@Slf4j
@RestController
public class RedissonController {


    @Autowired
    RedissonDelayQueue redissonDelayQueue;

    @GetMapping("/sendMessage")
    public String getToken() {
        redissonDelayQueue.offerTask("hello,world",50);
        return "ok";
    }


}
