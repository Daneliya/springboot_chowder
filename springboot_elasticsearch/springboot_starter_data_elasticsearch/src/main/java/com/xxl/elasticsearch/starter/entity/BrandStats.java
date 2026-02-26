package com.xxl.elasticsearch.starter.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 品牌统计结果类
 */
@Data
public class BrandStats {
    private String brand;
    private Long count;
    private BigDecimal avgPrice;
    // getters and setters
}
