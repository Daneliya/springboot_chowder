package com.xxl.elasticsearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxl.elasticsearch.pojo.Hotel;
import com.xxl.elasticsearch.pojo.PageResult;
import com.xxl.elasticsearch.pojo.RequestParams;

import java.util.List;
import java.util.Map;

/**
 * 酒店服务层
 *
 * @author xxl
 * @date 2026/2/24 11:34
 */
public interface IHotelService extends IService<Hotel> {

    PageResult search(RequestParams params);

    Map<String, List<String>> getFilters(RequestParams params);

    List<String> getSuggestion(String key);

    void deleteById(Long hotelId);

    void saveById(Long hotelId);
}
