package com.xxl.resilience4j.controller.service;

import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * 超时
 *
 * @Author: xxl
 * @Date: 2025/3/20 13:45
 */
@Service
public class TimeoutService {

    @TimeLimiter(name = "timeoutService", fallbackMethod = "fallback")
    public CompletableFuture<String> timeoutExample() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000); // 模拟长时间处理，这里设置为5秒
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Success";
        });
    }

    public CompletableFuture<String> fallback(Throwable t) {
        return CompletableFuture.completedFuture("fallback: timeout!");
    }
}