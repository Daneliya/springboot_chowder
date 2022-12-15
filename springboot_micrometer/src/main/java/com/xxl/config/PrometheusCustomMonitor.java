package com.xxl.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author xxl
 * @date 2022/12/16 0:00
 */
@Component
public class PrometheusCustomMonitor {
    /**
     * 上报拨打请求次数
     */
    @Getter
    private Counter reportDialRequestCount;
    /**
     * 上报拨打URL
     */
    @Value("${lx.call-result-report.url}")
    private String callReportUrl;

    /**
     * 上报拨打响应时间
     */
    @Getter
    private Timer reportDialResponseTime;

    @Getter
    private final MeterRegistry registry;


    @Autowired
    public PrometheusCustomMonitor(MeterRegistry registry) {
        this.registry = registry;
    }

    @PostConstruct
    private void init() {
        reportDialRequestCount = registry.counter("go_api_report_dial_request_count", "url", callReportUrl);
        reportDialResponseTime = registry.timer("go_api_report_dial_response_time", "url", callReportUrl);
    }
}