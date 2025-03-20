package com.xxl.resilience4j.controller.service;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * 限速
 *
 * @Author: xxl
 * @Date: 2025/3/20 13:44
 */
@Service
public class RateLimiterService {

    @RateLimiter(name = "ratelimiterService", fallbackMethod = "fallbackRateLimiter")
    public CompletableFuture<String> rateLimiterExample() {
        return CompletableFuture.supplyAsync(() -> "It is Success!");
    }

    public CompletableFuture<String> fallbackRateLimiter(Exception e) {
        return CompletableFuture.completedFuture("Too many requests");
    }
}
