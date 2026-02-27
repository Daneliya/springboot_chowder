package com.xxl.elasticsearch.starter.service;

import com.xxl.elasticsearch.starter.entity.Product;

import java.util.List;
import java.util.Map;

/**
 * 高级 Service
 *
 * @author xxl
 * @date 2026/2/27 16:57
 */
public interface ProductAdvancedService {

    /**
     * 使用ElasticsearchClient进行搜索
     */
    List<Product> searchWithClient(String keyword);

    /**
     * 批量索引文档
     */
    void bulkIndexProducts(List<Product> products);

    /**
     * 更新单个字段
     */
    void updateField(String id, String field, Object value);

    /**
     * 获取索引统计信息
     */
    Map<String, Object> getIndexStats();
}
