package com.xxl.elasticsearch.starter.service;

import com.xxl.elasticsearch.starter.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 产品 Service 接口
 */
public interface ProductService {

    // ============ CRUD 操作 ============

    /**
     * 保存或更新产品
     */
    Product save(Product product);

    /**
     * 批量保存产品
     */
    List<Product> saveAll(List<Product> products);

    /**
     * 根据ID查找产品
     */
    Product findById(String id);

    /**
     * 查找所有产品
     */
    List<Product> findAll();

    /**
     * 分页查找所有产品
     */
    Page<Product> findAll(Pageable pageable);

    /**
     * 根据ID删除产品
     */
    void deleteById(String id);

    /**
     * 删除所有产品
     */
    void deleteAll();

    // ============ 查询操作 ============

    /**
     * 根据名称搜索
     */
    List<Product> searchByName(String name);

    /**
     * 根据分类查询
     */
    List<Product> findByCategory(String category);

    /**
     * 根据价格范围查询
     */
    List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * 根据品牌查询
     */
    List<Product> findByBrand(String brand);

    /**
     * 根据标签查询
     */
    List<Product> findByTag(String tag);

    /**
     * 搜索上架商品
     */
    List<Product> findActiveProducts();

    // ============ 高级搜索 ============

    /**
     * 复杂条件搜索
     */
    Page<Product> complexSearch(String keyword,
                                List<String> categories,
                                BigDecimal minPrice,
                                BigDecimal maxPrice,
                                List<String> brands,
                                Pageable pageable);

    /**
     * 全文搜索
     */
    Page<Product> fullTextSearch(String keyword, Pageable pageable);

    /**
     * 自动补全
     */
    List<String> suggest(String prefix);

    // ============ 聚合操作 ============

    /**
     * 按分类统计
     */
    Map<String, Long> countByCategory();

    /**
     * 按品牌统计
     */
    Map<String, Long> countByBrand();

    /**
     * 价格分布统计
     */
    Map<String, Long> priceDistribution();

    // ============ 批量操作 ============

    /**
     * 批量更新库存
     */
    void bulkUpdateStock(Map<String, Integer> stockUpdates);

    /**
     * 批量更新价格
     */
    void bulkUpdatePrice(Map<String, BigDecimal> priceUpdates);
}