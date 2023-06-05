package com.xxl.caffeine.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.xxl.caffeine.contants.CacheType;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * caffeine缓存配置类
 *
 * @author xxl
 * @date 2023/6/5 23:01
 */
@Configuration
public class CaffeineConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<CaffeineCache> caffeineCaches = new ArrayList<>();
        for (CacheType cacheType : CacheType.values()) {
            caffeineCaches.add(new CaffeineCache(cacheType.name(),
                    Caffeine.newBuilder()
                            .expireAfterWrite(cacheType.getExpires(), TimeUnit.SECONDS)
                            .build()));
        }
        cacheManager.setCaches(caffeineCaches);
        return cacheManager;
    }

}