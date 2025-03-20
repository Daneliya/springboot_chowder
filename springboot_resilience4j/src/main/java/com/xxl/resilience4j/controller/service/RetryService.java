package com.xxl.resilience4j.controller.service;

import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 缓存+重试
 *
 * @Author: xxl
 * @Date: 2025/3/20 13:46
 */
@Service
public class RetryService {
    private static final Logger logger = LoggerFactory.getLogger(RetryService.class);

    @Cacheable(value = "items", key = "#id")
    @Retry(name = "retryService", fallbackMethod = "fallback")
    public String getItemById(String id) {
        logger.info("Fetching item with id {}", id);
        // 模拟数据库调用，可能会引发异常
        System.out.println(Math.random());
        if (Math.random() > 0.8) {
            throw new RuntimeException("Simulated database error");
        }
        return "Cache " + id;
    }

    // 回退方法
    public String fallback(String id, RuntimeException e) {
        return "Fallback : " + id;
    }
}