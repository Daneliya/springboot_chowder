package com.xxl.redisson.listener;

import com.xxl.redisson.handle.RedisDelayQueueHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 喜爱度 处理类
 *
 * @author xxl
 * @date 2024/1/23 20:01
 */
@Component
@Slf4j
public class OrderLabel implements RedisDelayQueueHandle<Map> {

    @Override
    public void execute(Map map) {
//        log.info("(商品浏览增加喜爱度) {}", map);
        // TODO 商品浏览增加喜爱度处理业务...
        System.out.println("商品浏览增加喜爱度");
    }
}
