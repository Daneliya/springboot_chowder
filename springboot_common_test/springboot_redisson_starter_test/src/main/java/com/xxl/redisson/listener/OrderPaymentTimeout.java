package com.xxl.redisson.listener;

import com.xxl.redisson.handle.RedisDelayQueueHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 超时处理类
 *
 * @author xxl
 * @date 2023/10/07
 */
@Component
@Slf4j
public class OrderPaymentTimeout implements RedisDelayQueueHandle<Map> {

    @Override
    public void execute(Map map) {
//        log.info("(收到订单支付超时延迟消息) {}", map);
        // TODO 订单支付超时，自动取消订单处理业务...
        System.out.println("订单支付超时，自动取消订单处理业务");
    }

}
