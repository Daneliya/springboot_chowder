package com.xxl.elasticsearch.starter.repository.custom;

import com.xxl.elasticsearch.starter.entity.BrandStats;
import com.xxl.elasticsearch.starter.entity.PriceRangeStats;
import com.xxl.elasticsearch.starter.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * 自定义Repository接口
 *
 * @author xxl
 * @date 2026/2/26 09:50
 */
public interface CustomProductRepository {

    /**
     * 复杂搜索：多条件组合查询
     */
    Page<Product> complexSearch(String keyword,
                                List<String> categories,
                                BigDecimal minPrice,
                                BigDecimal maxPrice,
                                List<String> brands,
                                Pageable pageable);

    /**
     * 全文搜索并高亮显示
     */
    Page<Product> searchWithHighlight(String keyword, Pageable pageable);

    /**
     * 聚合查询：按品牌分组统计
     */
    List<BrandStats> groupByBrand();

    /**
     * 聚合查询：价格范围分布
     */
    List<PriceRangeStats> priceRangeDistribution();

    /**
     * 地理空间搜索：附近的产品
     */
    List<Product> searchNearby(Double lat, Double lon, Double distance);

    /**
     * 自动补全建议
     */
    List<String> getSuggestions(String prefix);

    /**
     * 批量更新库存
     */
    void bulkUpdateStock(List<String> productIds, Integer stockDelta);
}

