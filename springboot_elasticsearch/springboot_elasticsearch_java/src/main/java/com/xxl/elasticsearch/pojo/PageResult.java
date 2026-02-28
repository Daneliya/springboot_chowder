package com.xxl.elasticsearch.pojo;

import com.xxl.elasticsearch.dto.HotelDoc;
import lombok.Data;

import java.util.List;

/**
 * 酒店 结果实体
 *
 * @author xxl
 * @date 2026/2/24 11:33
 */
@Data
public class PageResult {
    private Long total;
    private List<HotelDoc> hotels;

    public PageResult() {
    }

    public PageResult(Long total, List<HotelDoc> hotels) {
        this.total = total;
        this.hotels = hotels;
    }
}
