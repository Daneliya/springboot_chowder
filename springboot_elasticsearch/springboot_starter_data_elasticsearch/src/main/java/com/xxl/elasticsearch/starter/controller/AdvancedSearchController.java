package com.xxl.elasticsearch.starter.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import com.xxl.elasticsearch.starter.entity.Product;
import com.xxl.elasticsearch.starter.service.ProductAdvancedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 高级搜索控制器
 *
 * @author xxl
 * @date 2026/2/26 10:42
 */
@Slf4j
@RestController
@RequestMapping("/api/advanced")
@RequiredArgsConstructor
public class AdvancedSearchController {

    private final ProductAdvancedService productAdvancedService;

    private final ElasticsearchClient elasticsearchClient;

    /**
     * 高级搜索
     */
    @GetMapping("/search")
    public ResponseEntity<List<Product>> advancedSearch(@RequestParam String keyword) {
        log.info("高级搜索，关键字: {}", keyword);
        List<Product> products = productAdvancedService.searchWithClient(keyword);
        return ResponseEntity.ok(products);
    }

    /**
     * 获取索引统计
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        log.info("获取索引统计");
        Map<String, Object> stats = productAdvancedService.getIndexStats();
        return ResponseEntity.ok(stats);
    }

    /**
     * 聚合查询示例
     */
    @GetMapping("/aggregations")
    public ResponseEntity<?> getAggregations() {
        log.info("聚合查询示例");
        Map<String, Aggregate> stats = productAdvancedService.getAggregations();
        return ResponseEntity.ok(stats);
    }

    /**
     * 高亮查询示例
     */
    @GetMapping("/highlight")
    public ResponseEntity<?> testHighlight(@RequestParam String keyword) {
        log.info("高亮查询示例");
        List<Product> products = productAdvancedService.testHighlight(keyword);
        return ResponseEntity.ok(products);
    }
}
