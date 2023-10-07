//package com.xxl.redisson.controller;
//
///**
// * 我们用Redisson实现一个优雅的延迟队列
// *
// * @author xxl
// * @date 2023/10/07
// */
//public class RedissonLuaController {
//
//    @Autowired
//    RedissonDelayQueue redissonDelayQueue;
//
//    @GetMapping("/sendMessage")
//    public String getToken() {
//        redissonDelayQueue.offerTask("hello,world", 50);
//        return "ok";
//    }
//
//}
