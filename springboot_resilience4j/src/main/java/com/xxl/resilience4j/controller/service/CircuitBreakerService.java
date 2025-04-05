package com.xxl.resilience4j.controller.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

/**
 * 熔断
 *
 * @Author: xxl
 * @Date: 2025/3/20 13:41
 */
@Service
public class CircuitBreakerService {

    @CircuitBreaker(name = "circuitbreakerService", fallbackMethod = "fallback")
    public String CircuitBreaker() {
        if (Math.random() > 0.5) {
            throw new RuntimeException("It is Failure!");
        }
        return "It is Successfully!";
    }

    public String fallback(Throwable ex) {
        return "Fallback : " + ex.getMessage();
    }
}
