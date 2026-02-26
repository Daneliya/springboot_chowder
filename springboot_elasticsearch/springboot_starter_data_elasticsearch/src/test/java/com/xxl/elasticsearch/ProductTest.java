//package com.xxl.elasticsearch;
//
//import com.xxl.elasticsearch.starter.entity.Product;
//import com.xxl.elasticsearch.starter.service.ProductService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * 产品测试类
// */
//@Slf4j
//@SpringBootTest
//public class ProductTest {
//
//    @Autowired
//    private ProductService productService;
//
//    /**
//     * 测试创建产品
//     */
//    @Test
//    public void testCreateProduct() {
//        Product product = Product.builder()
//                .name("Apple iPhone 15 Pro")
//                .description("苹果 iPhone 15 Pro 智能手机，搭载 A17 Pro 芯片")
//                .price(new BigDecimal(9999))
//                .stock(100)
//                .category("手机")
//                .brand("Apple")
//                .tags(Arrays.asList("智能手机", "5G", "iOS"))
//                .isActive(true)
//                .createTime(LocalDateTime.now())
//                .updateTime(LocalDateTime.now())
//                .build();
//
//        Product createdProduct = productService.createProduct(product);
//        assertNotNull(createdProduct);
//        assertNotNull(createdProduct.getId());
//        assertEquals("Apple iPhone 15 Pro", createdProduct.getName());
//        log.info("创建产品成功: {}", createdProduct);
//    }
//
//    /**
//     * 测试查询产品
//     */
//    @Test
//    public void testGetProductById() {
//        // 先创建一个产品
//        Product product = Product.builder()
//                .name("Samsung Galaxy S24 Ultra")
//                .description("三星 Galaxy S24 Ultra 智能手机，搭载 Snapdragon 8 Gen 3 芯片")
//                .price(new BigDecimal(8999))
//                .stock(80)
//                .category("手机")
//                .brand("Samsung")
//                .tags(Arrays.asList("智能手机", "5G", "Android"))
//                .isActive(true)
//                .build();
//
//        Product createdProduct = productService.createProduct(product);
//        String productId = createdProduct.getId();
//
//        // 查询产品
//        Optional<Product> foundProduct = productService.getProductById(productId);
//        assertTrue(foundProduct.isPresent());
//        assertEquals("Samsung Galaxy S24 Ultra", foundProduct.get().getName());
//        log.info("查询产品成功: {}", foundProduct.get());
//    }
//
//    /**
//     * 测试查询所有产品
//     */
//    @Test
//    public void testGetAllProducts() {
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
//        Page<Product> products = productService.getAllProducts(pageable);
//        assertNotNull(products);
//        log.info("查询所有产品，总数: {}", products.getTotalElements());
//        log.info("产品列表: {}", products.getContent());
//    }
//
//    /**
//     * 测试根据分类查询产品
//     */
//    @Test
//    public void testGetProductsByCategory() {
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<Product> products = productService.getProductsByCategory("手机", pageable);
//        assertNotNull(products);
//        log.info("根据分类查询产品，总数: {}", products.getTotalElements());
//    }
//
//    /**
//     * 测试搜索产品
//     */
//    @Test
//    public void testSearchProducts() {
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<Product> products = productService.searchProducts("iPhone", pageable);
//        assertNotNull(products);
//        log.info("搜索产品，关键词: iPhone, 总数: {}", products.getTotalElements());
//        log.info("搜索结果: {}", products.getContent());
//    }
//
//    /**
//     * 测试更新产品
//     */
//    @Test
//    public void testUpdateProduct() {
//        // 先创建一个产品
//        Product product = Product.builder()
//                .name("Huawei Mate 60 Pro")
//                .description("华为 Mate 60 Pro 智能手机，搭载麒麟 9000S 芯片")
//                .price(new BigDecimal(6999))
//                .stock(50)
//                .category("手机")
//                .brand("Huawei")
//                .tags(Arrays.asList("智能手机", "5G", "HarmonyOS"))
//                .isActive(true)
//                .build();
//
//        Product createdProduct = productService.createProduct(product);
//        String productId = createdProduct.getId();
//
//        // 更新产品
//        Product updatedProduct = Product.builder()
//                .name("Huawei Mate 60 Pro+")
//                .description("华为 Mate 60 Pro+ 智能手机，搭载麒麟 9000S 芯片，更大内存")
//                .price(new BigDecimal(7999))
//                .stock(30)
//                .category("手机")
//                .brand("Huawei")
//                .tags(Arrays.asList("智能手机", "5G", "HarmonyOS", "大内存"))
//                .isActive(true)
//                .build();
//
//        Product result = productService.updateProduct(productId, updatedProduct);
//        assertNotNull(result);
//        assertEquals("Huawei Mate 60 Pro+", result.getName());
//        assertEquals(new BigDecimal(7999), result.getPrice());
//        log.info("更新产品成功: {}", result);
//    }
//
//    /**
//     * 测试删除产品
//     */
//    @Test
//    public void testDeleteProduct() {
//        // 先创建一个产品
//        Product product = Product.builder()
//                .name("Xiaomi 14 Pro")
//                .description("小米 14 Pro 智能手机，搭载 Snapdragon 8 Gen 3 芯片")
//                .price(new BigDecimal(4999))
//                .stock(120)
//                .category("手机")
//                .brand("Xiaomi")
//                .tags(Arrays.asList("智能手机", "5G", "MIUI"))
//                .isActive(true)
//                .build();
//
//        Product createdProduct = productService.createProduct(product);
//        String productId = createdProduct.getId();
//
//        // 删除产品
//        productService.deleteProduct(productId);
//
//        // 验证产品是否删除
//        Optional<Product> deletedProduct = productService.getProductById(productId);
//        assertFalse(deletedProduct.isPresent());
//        log.info("删除产品成功，ID: {}", productId);
//    }
//}