package com.xxl.elasticsearch.pojo;

import lombok.Data;

/**
 * 酒店 请求参数
 *
 * @author xxl
 * @date 2026/2/24 11:33
 */
@Data
public class RequestParams {
    private String key;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String brand;
    private String city;
    private String starName;
    private Integer minPrice;
    private Integer maxPrice;
    private String location;
}
