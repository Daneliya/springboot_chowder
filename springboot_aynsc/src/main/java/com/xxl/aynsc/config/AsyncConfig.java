package com.xxl.aynsc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PreDestroy;

/**
 * 自定义线程池
 *
 * @Author: xxl
 * @Date: 2024/11/22 15:38
 */
@Configuration
public class AsyncConfig {

    private ThreadPoolTaskExecutor taskExecutor;

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4); // 核心线程数
        executor.setMaxPoolSize(8); // 最大线程数
        executor.setQueueCapacity(100); // 队列容量
        executor.setThreadNamePrefix("async-thread-"); // 线程名称前缀
        executor.initialize();
        return executor;
    }

    @PreDestroy
    public void shutdown() {
        taskExecutor.shutdown();
    }

}
