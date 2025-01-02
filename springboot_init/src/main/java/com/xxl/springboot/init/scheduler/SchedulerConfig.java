package com.xxl.springboot.init.scheduler;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 * @author xxl
 * @date 2025/1/2 0:31
 */
@Configuration            // 定时调度的配置类一定要实现指定的父接口
public class SchedulerConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {        // 开启线程调度池
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));    // 10个线程池
    }
}
