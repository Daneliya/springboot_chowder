package com.xxl.elasticsearch.starter.entity;

import lombok.Data;

/**
 * 价格范围统计结果类
 */
@Data
public class PriceRangeStats {
    private String range;
    private Long count;
    // getters and setters
}
