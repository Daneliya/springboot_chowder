package com.xxl.elasticsearch.starter.repository;

import com.xxl.elasticsearch.starter.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 产品Repository接口
 * 继承ElasticsearchRepository，提供基础的CRUD操作
 */
@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, String> {

    // ============ 基础查询方法 ============

    /**
     * 根据名称查询（精确匹配）
     */
    List<Product> findByName(String name);

    /**
     * 根据名称模糊查询
     */
    List<Product> findByNameContaining(String name);

    /**
     * 根据名称或描述查询
     */
    List<Product> findByNameOrDescription(String name, String description);

    /**
     * 根据分类查询
     */
    List<Product> findByCategory(String category);

    /**
     * 根据品牌查询
     */
    List<Product> findByBrand(String brand);

    /**
     * 根据价格范围查询
     */
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * 根据库存查询
     */
    List<Product> findByStockGreaterThan(Integer stock);

    /**
     * 查询上架商品
     */
    List<Product> findByIsActiveTrue();

    /**
     * 根据多个分类查询
     */
    List<Product> findByCategoryIn(List<String> categories);

    /**
     * 根据标签包含查询
     */
    List<Product> findByTagsContains(String tag);

    // ============ 分页查询 ============

    /**
     * 分页查询所有商品
     */
    Page<Product> findAll(Pageable pageable);

    /**
     * 根据名称分页查询
     */
    Page<Product> findByNameContaining(String name, Pageable pageable);

    /**
     * 根据分类分页查询
     */
    Page<Product> findByCategory(String category, Pageable pageable);

    /**
     * 根据品牌分页查询
     */
    Page<Product> findByBrand(String brand, Pageable pageable);

    /**
     * 根据价格范围分页查询
     */
    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    // ============ 排序查询 ============

    /**
     * 根据价格升序查询
     */
    List<Product> findByOrderByPriceAsc();

    /**
     * 根据价格降序查询
     */
    List<Product> findByOrderByPriceDesc();

    /**
     * 根据创建时间降序查询
     */
    List<Product> findByOrderByCreateTimeDesc();

    // ============ 自定义查询 ============

    /**
     * 根据名称和分类查询
     */
    List<Product> findByNameAndCategory(String name, String category);

    /**
     * 根据名称和价格范围查询
     */
    List<Product> findByNameAndPriceBetween(String name, BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * 统计某个分类的商品数量
     */
    long countByCategory(String category);

    /**
     * 删除某个品牌的商品
     */
    void deleteByBrand(String brand);
}