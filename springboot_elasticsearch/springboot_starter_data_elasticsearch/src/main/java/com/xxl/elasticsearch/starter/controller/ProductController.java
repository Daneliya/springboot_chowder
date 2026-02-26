package com.xxl.elasticsearch.starter.controller;

import com.xxl.elasticsearch.starter.entity.Product;
import com.xxl.elasticsearch.starter.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 产品控制器
 * 处理产品相关的HTTP请求
 */
@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // ============ CRUD 接口 ============

    /**
     * 创建产品
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        log.info("创建产品: {}", product.getName());
        Product savedProduct = productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    /**
     * 批量创建产品
     */
    @PostMapping("/batch")
    public ResponseEntity<List<Product>> batchCreateProducts(@RequestBody List<Product> products) {
        log.info("批量创建产品，数量: {}", products.size());
        List<Product> savedProducts = productService.saveAll(products);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProducts);
    }

    /**
     * 根据ID获取产品
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        log.info("获取产品，ID: {}", id);
        Product product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    /**
     * 获取所有产品
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("获取所有产品");
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    /**
     * 分页获取产品
     */
    @GetMapping("/page")
    public ResponseEntity<Page<Product>> getProductsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createTime") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        log.info("分页获取产品，第 {} 页，每页 {} 条", page, size);

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction)
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<Product> products = productService.findAll(pageable);
        return ResponseEntity.ok(products);
    }

    /**
     * 更新产品
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable String id,
            @RequestBody Product product) {

        log.info("更新产品，ID: {}", id);

        // 确保ID一致
        product.setId(id);
        Product updatedProduct = productService.save(product);

        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * 删除产品
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        log.info("删除产品，ID: {}", id);
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ============ 搜索接口 ============

    /**
     * 根据名称搜索
     */
    @GetMapping("/search/name")
    public ResponseEntity<List<Product>> searchByName(
            @RequestParam String name) {

        log.info("根据名称搜索: {}", name);
        List<Product> products = productService.searchByName(name);
        return ResponseEntity.ok(products);
    }

    /**
     * 根据分类查询
     */
    @GetMapping("/search/category")
    public ResponseEntity<List<Product>> searchByCategory(
            @RequestParam String category) {

        log.info("根据分类查询: {}", category);
        List<Product> products = productService.findByCategory(category);
        return ResponseEntity.ok(products);
    }

    /**
     * 根据价格范围查询
     */
    @GetMapping("/search/price-range")
    public ResponseEntity<List<Product>> searchByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {

        log.info("根据价格范围查询: {} - {}", minPrice, maxPrice);
        List<Product> products = productService.findByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    /**
     * 根据品牌查询
     */
    @GetMapping("/search/brand")
    public ResponseEntity<List<Product>> searchByBrand(
            @RequestParam String brand) {

        log.info("根据品牌查询: {}", brand);
        List<Product> products = productService.findByBrand(brand);
        return ResponseEntity.ok(products);
    }

    /**
     * 根据标签查询
     */
    @GetMapping("/search/tag")
    public ResponseEntity<List<Product>> searchByTag(
            @RequestParam String tag) {

        log.info("根据标签查询: {}", tag);
        List<Product> products = productService.findByTag(tag);
        return ResponseEntity.ok(products);
    }

    /**
     * 查询上架商品
     */
    @GetMapping("/active")
    public ResponseEntity<List<Product>> getActiveProducts() {
        log.info("查询上架商品");
        List<Product> products = productService.findActiveProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * 复杂条件搜索
     */
    @GetMapping("/search/complex")
    public ResponseEntity<Page<Product>> complexSearch(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) List<String> brands,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("复杂条件搜索，关键字: {}", keyword);

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.complexSearch(
                keyword, categories, minPrice, maxPrice, brands, pageable);

        return ResponseEntity.ok(products);
    }

    /**
     * 全文搜索
     */
    @GetMapping("/search/full-text")
    public ResponseEntity<Page<Product>> fullTextSearch(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("全文搜索，关键字: {}", keyword);

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.fullTextSearch(keyword, pageable);

        return ResponseEntity.ok(products);
    }

    // ============ 聚合统计接口 ============

    /**
     * 按分类统计
     */
    @GetMapping("/stats/category")
    public ResponseEntity<Map<String, Long>> statsByCategory() {
        log.info("按分类统计");
        Map<String, Long> stats = productService.countByCategory();
        return ResponseEntity.ok(stats);
    }

    /**
     * 按品牌统计
     */
    @GetMapping("/stats/brand")
    public ResponseEntity<Map<String, Long>> statsByBrand() {
        log.info("按品牌统计");
        Map<String, Long> stats = productService.countByBrand();
        return ResponseEntity.ok(stats);
    }

    /**
     * 价格分布统计
     */
    @GetMapping("/stats/price-distribution")
    public ResponseEntity<Map<String, Long>> priceDistribution() {
        log.info("价格分布统计");
        Map<String, Long> distribution = productService.priceDistribution();
        return ResponseEntity.ok(distribution);
    }

    // ============ 批量操作接口 ============

    /**
     * 批量更新库存
     */
    @PostMapping("/bulk/stock")
    public ResponseEntity<Void> bulkUpdateStock(
            @RequestBody Map<String, Integer> stockUpdates) {

        log.info("批量更新库存，更新数量: {}", stockUpdates.size());
        productService.bulkUpdateStock(stockUpdates);
        return ResponseEntity.ok().build();
    }

    /**
     * 批量更新价格
     */
    @PostMapping("/bulk/price")
    public ResponseEntity<Void> bulkUpdatePrice(
            @RequestBody Map<String, BigDecimal> priceUpdates) {

        log.info("批量更新价格，更新数量: {}", priceUpdates.size());
        productService.bulkUpdatePrice(priceUpdates);
        return ResponseEntity.ok().build();
    }
}