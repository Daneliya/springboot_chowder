package com.xxl.elasticsearch.starter.service.impl;

import com.xxl.elasticsearch.starter.entity.Product;
import com.xxl.elasticsearch.starter.repository.ProductRepository;
import com.xxl.elasticsearch.starter.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * 产品 Service 实现类
 *
 * @author xxl
 * @date 2026/2/26 09:57
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Product save(Product product) {
        if (product.getId() == null) {
//            product.setCreateTime(LocalDateTime.now());
            product.setCreateTime(new Date());
        }
//        product.setUpdateTime(LocalDateTime.now());
        product.setUpdateTime(new Date());
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public List<Product> saveAll(List<Product> products) {
//        LocalDateTime now = LocalDateTime.now();
        Date now = new Date();
        products.forEach(product -> {
            if (product.getId() == null) {
                product.setCreateTime(now);
            }
            product.setUpdateTime(now);
        });
        return (List<Product>) productRepository.saveAll(products);
    }

    @Override
    public Product findById(String id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() ->
                new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public List<Product> findAll() {
        Iterable<Product> products = productRepository.findAll();
        return StreamSupport.stream(products.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        productRepository.deleteAll();
    }

    @Override
    public List<Product> searchByName(String name) {
        return productRepository.findByNameContaining(name);
    }

    @Override
    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Product> findByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> findByTag(String tag) {
        return productRepository.findByTagsContains(tag);
    }

    @Override
    public List<Product> findActiveProducts() {
        return productRepository.findByIsActiveTrue();
    }

    @Override
    public Page<Product> complexSearch(String keyword,
                                       List<String> categories,
                                       BigDecimal minPrice,
                                       BigDecimal maxPrice,
                                       List<String> brands,
                                       Pageable pageable) {
        // 这里调用自定义Repository的方法
        // 需要注入CustomProductRepository
        throw new UnsupportedOperationException("需要实现自定义Repository");
    }

    @Override
    public Page<Product> fullTextSearch(String keyword, Pageable pageable) {
        // 使用简单的方式实现
        return productRepository.findByNameContaining(keyword, pageable);
    }

    @Override
    public List<String> suggest(String prefix) {
        // 实现自动补全逻辑
        throw new UnsupportedOperationException("需要实现自动补全");
    }

    @Override
    public Map<String, Long> countByCategory() {
        // 实现聚合查询
        throw new UnsupportedOperationException("需要实现聚合查询");
    }

    @Override
    public Map<String, Long> countByBrand() {
        throw new UnsupportedOperationException("需要实现聚合查询");
    }

    @Override
    public Map<String, Long> priceDistribution() {
        throw new UnsupportedOperationException("需要实现聚合查询");
    }

    @Override
    @Transactional
    public void bulkUpdateStock(Map<String, Integer> stockUpdates) {
        stockUpdates.forEach((productId, stockDelta) -> {
            Optional<Product> productOpt = productRepository.findById(productId);
            productOpt.ifPresent(product -> {
                int newStock = product.getStock() + stockDelta;
                product.setStock(newStock);
//                product.setUpdateTime(LocalDateTime.now());
                product.setUpdateTime(new Date());
                productRepository.save(product);
            });
        });
    }

    @Override
    @Transactional
    public void bulkUpdatePrice(Map<String, BigDecimal> priceUpdates) {
        priceUpdates.forEach((productId, newPrice) -> {
            Optional<Product> productOpt = productRepository.findById(productId);
            productOpt.ifPresent(product -> {
                product.setPrice(newPrice);
//                product.setUpdateTime(LocalDateTime.now());
                product.setUpdateTime(new Date());
                productRepository.save(product);
            });
        });
    }
}
