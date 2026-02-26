package com.xxl.elasticsearch.starter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地理坐标类
 *
 * @author xxl
 * @date 2026/2/26 09:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoPoint {
    private Double lat;
    private Double lon;
}