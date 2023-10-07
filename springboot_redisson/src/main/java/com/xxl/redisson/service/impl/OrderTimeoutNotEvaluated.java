package com.xxl.redisson.service.impl;

import com.xxl.redisson.service.RedisDelayQueueHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 订单超时未评价处理类
 *
 * @author xxl
 * @date 2023/10/07
 */
@Component
@Slf4j
public class OrderTimeoutNotEvaluated implements RedisDelayQueueHandle<Map> {

    @Override
    public void execute(Map map) {
        log.info("(收到订单超时未评价延迟消息) {}", map);
        // TODO 订单超时未评价，系统默认好评处理业务...
        System.out.println("订单超时未评价，系统默认好评处理业务");
    }
}
