package com.xxl.resilience4j.controller.service;

import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 批隔离
 *
 * @Author: xxl
 * @Date: 2025/3/20 13:39
 */
@Service
public class BulkheadService {

    private static final Logger logger = LoggerFactory.getLogger(BulkheadService.class);

    @Bulkhead(name = "bulkheadService", fallbackMethod = "fallback")
    public String processRequest(String id) {
        logger.info("Processing request: {}", id);
        try {
            // 模拟处理延迟
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Processed: " + id;
    }

    // 回退方法
    public String fallback(String id, BulkheadFullException ex) {
        logger.error("Bulkhead is full. Falling back for request: {}", id, ex);
        return "Bulkhead is full. Please try again later.";
    }

}
