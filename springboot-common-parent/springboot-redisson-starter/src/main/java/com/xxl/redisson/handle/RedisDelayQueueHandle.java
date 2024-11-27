package com.xxl.redisson.handle;

/**
 * 延迟队列执行器（业务处理需要实现此接口）
 *
 * @author xxl
 * @date 2023/09/15
 */
public interface RedisDelayQueueHandle<T> {

    void execute(T t);

}