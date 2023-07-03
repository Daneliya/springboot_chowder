//package com.xxl.item.config;
//
//import com.github.benmanes.caffeine.cache.Cache;
//import com.github.benmanes.caffeine.cache.Caffeine;
//import com.xxl.item.pojo.Item;
//import com.xxl.item.pojo.ItemStock;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * caffeine 缓存配置
// *
// * @author xxl
// * @date 2022/12/6 0:40
// */
//@Configuration
//public class CaffeineConfig {
//
//    @Bean
//    public Cache<Long, Item> itemCache() {
//        return Caffeine.newBuilder()
//                .initialCapacity(100)
//                .maximumSize(10_000)
//                .build();
//    }
//
//    @Bean
//    public Cache<Long, ItemStock> stockCache() {
//        return Caffeine.newBuilder()
//                .initialCapacity(100)
//                .maximumSize(10_000)
//                .build();
//    }
//
//}
