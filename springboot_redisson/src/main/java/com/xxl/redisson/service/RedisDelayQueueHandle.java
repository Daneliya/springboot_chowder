package com.xxl.redisson.service;

/**
 * 延迟队列执行器
 *
 * @author xxl
 * @date 2023/10/07
 */
public interface RedisDelayQueueHandle<T> {

    void execute(T t);

}
