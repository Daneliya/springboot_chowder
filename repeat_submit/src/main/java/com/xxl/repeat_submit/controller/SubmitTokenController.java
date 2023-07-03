package com.xxl.repeat_submit.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xxl
 * @date 2022/11/17 23:37
 */
@RestController
@RequestMapping("/api")
public class SubmitTokenController {
//
//    /**
//     * SubmitToken过期时间
//     */
//    private static final Integer EXPIRE_TIME = 60;
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    /**
//     * 获取getSubmitToken
//     * @return
//     */
//    @RequestMapping("/getSubmitToken")
//    public ResResult getSubmitToken(){
//        String uuid = UUID.randomUUID().toString();
//        //存入redis
//        stringRedisTemplate.opsForValue().set(uuid, uuid, EXPIRE_TIME, TimeUnit.SECONDS);
//        return ResResult.getSuccess(uuid);
//    }
}