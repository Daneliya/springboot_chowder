package com.xxl.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author xxl
 * @date 2023/09/18
 */
@Configuration
public class MyRedissonConfig {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() throws IOException {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }


    @Bean
    public RBlockingQueue<String> blockingQueue(RedissonClient redissonClient) {
        return redissonClient.getBlockingQueue("TOKEN-RENEWAL");
    }

    @Bean
    public RDelayedQueue<String> delayedQueue(RBlockingQueue<String> blockingQueue,
                                              RedissonClient redissonClient) {
        return redissonClient.getDelayedQueue(blockingQueue);
    }
}

