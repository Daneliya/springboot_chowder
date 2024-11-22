package com.xxl.aynsc.config;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @Author: xxl
 * @Date: 2024/11/22 15:55
 */
@Component
public class ExecutorManager {

    private final ThreadPoolTaskExecutor taskExecutor;

    public ExecutorManager(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @PreDestroy
    public void shutdown() {
        taskExecutor.shutdown();
    }
}