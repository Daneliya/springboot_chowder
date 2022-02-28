package com.xxl.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置类
 *
 * @author xxl
 * @date 2022/2/28 23:55
 */
@Configuration
public class RedisConfig {

    public RedisTemplate<Object, Object> redidTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory); // 创建Redis连接

        GenericFastJsonRedisSerializer jsonRedisSerializer = new GenericFastJsonRedisSerializer(); // json 序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer(); // string 序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer); // key 的序列化方式是 string 类型
        redisTemplate.setValueSerializer(jsonRedisSerializer); // value 的序列化方式是 json 类型
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);

        return redisTemplate;
    }
}
