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

    /**
     * 全局搜索
     *
     * @param params 请求参数
     * @return 返回结果
     */
    PageResult search(RequestParams params);

    /**
     * 分组筛选项
     *
     * @param params 请求参数
     * @return 返回结果
     */
    Map<String, List<String>> getFilters(RequestParams params);

    /**
     * 搜索建议
     *
     * @param key 请求参数
     * @return 返回结果
     */
    List<String> getSuggestion(String key);

    void deleteById(Long hotelId);

    void saveById(Long hotelId);
}
