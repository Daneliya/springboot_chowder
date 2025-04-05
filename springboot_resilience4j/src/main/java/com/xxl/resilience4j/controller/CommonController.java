package com.xxl.resilience4j.controller;

import com.xxl.resilience4j.controller.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * @Author: xxl
 * @Date: 2025/3/20 13:38
 */
@RestController
public class CommonController {

    @Autowired
    TimeoutService timeoutService;

    @Autowired
    RateLimiterService rateLimiterService;

    @Autowired
    CircuitBreakerService circuitBreakerService;

    @Autowired
    RetryService retryService;

    @Autowired
    BulkheadService bulkheadService;

    /**
     * 模拟超时
     *
     * @return
     */
    @GetMapping("/timeout")
    public CompletableFuture<String> timeout() {
        return timeoutService.timeoutExample();
    }

    /**
     * 模拟限速
     *
     * @return
     */
    @GetMapping("/rateLimiter")
    public CompletableFuture<String> rateLimiter() {
        return rateLimiterService.rateLimiterExample();
    }

    /**
     * 模拟回退
     *
     * @return
     */
    @GetMapping("/circuitBreaker")
    public ResponseEntity<String> circuitBreaker() {
        return ResponseEntity.ok(circuitBreakerService.CircuitBreaker());
    }

    /**
     * 模拟重试
     *
     * @param id
     * @return
     */
    @GetMapping("/retry/{id}")
    public String getItemById(@PathVariable String id) {
        return retryService.getItemById(id);
    }

    /**
     * 模拟批隔离
     *
     * @param id
     * @return
     */
    @GetMapping("/process/{id}")
    public String processRequest(@PathVariable String id) {
        return bulkheadService.processRequest(id);
    }

}
