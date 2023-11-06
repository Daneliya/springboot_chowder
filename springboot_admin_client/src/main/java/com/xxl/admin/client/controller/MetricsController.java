package com.xxl.admin.client.controller;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xxl
 * @date 2023/11/6 22:42
 */

@RestController
public class MetricsController {

    @GetMapping("/sayHi")
    public String sayHi(String productNo) throws InterruptedException {
        // 线程长请求
        //Thread.sleep(5000);
        // 定制基于Metrics的计数器
        Metrics.counter("order.counter", "productNo", productNo).increment();

        // 定制基于Metrics的定时器
        Timer timer = Metrics.timer("order.timer");
        timer.record(() -> {
            System.out.println("success");
        });

        // 定制基于Metrics的仪表  记录单个值  一般集合数量
        Metrics.gauge("order.gauge", 1);

        // 定制基于Metrics的摘要 一般用来记录 百分比数值（缓存命中率)
        DistributionSummary summary = Metrics.summary("redis.hitRate");
        summary.record(1.5);
        return "success";
    }

    @GetMapping("/sayHi2")
    public String sayHi() throws InterruptedException {
        return "success2";
    }

}
