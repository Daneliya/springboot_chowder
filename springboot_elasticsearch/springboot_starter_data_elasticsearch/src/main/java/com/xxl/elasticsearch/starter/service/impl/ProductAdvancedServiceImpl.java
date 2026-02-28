package com.xxl.elasticsearch.starter.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.indices.IndicesStatsResponse;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.xxl.elasticsearch.starter.entity.Product;
import com.xxl.elasticsearch.starter.service.ProductAdvancedService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 高级 Service
 *
 * @author xxl
 * @date 2026/2/26 09:59
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductAdvancedServiceImpl implements ProductAdvancedService {

    private final ElasticsearchClient elasticsearchClient;

    /**
     * 使用ElasticsearchClient进行搜索
     */
    @Override
    public List<Product> searchWithClient(String keyword) {
        try {
            SearchResponse<Product> response = elasticsearchClient.search(
                    s -> s
                            .index("products")
                            .query(q -> q
                                            .bool(b -> b
                                                            .must(m -> m
                                                                            .match(t -> t
                                                                                    .field("description")
                                                                                    .query(keyword)
                                                                            )
//                                                    .multiMatch( t -> t
//                                                            .fields(List.of("name", "description"))
//                                                            .query(keyword)
//                                                    )
                                                            )
                                                            .filter(f -> f
                                                                    .term(t -> t
                                                                            .field("isActive")
                                                                            .value(true)
                                                                    )
                                                            )
                                            )
                            )
                            .size(100),
                    Product.class
            );

            return response.hits().hits().stream()
                    .map(Hit::source)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            log.error("搜索失败", e);
            throw new RuntimeException("搜索失败", e);
        }
    }

    /**
     * 批量索引文档
     */
    @Override
    public void bulkIndexProducts(List<Product> products) {
        try {
            BulkRequest.Builder br = new BulkRequest.Builder();

            for (Product product : products) {
                br.operations(op -> op
                        .index(idx -> idx
                                .index("products")
                                .id(product.getId())
                                .document(product)
                        )
                );
            }

            BulkResponse response = elasticsearchClient.bulk(br.build());

            if (response.errors()) {
                log.error("批量索引存在错误");
                response.items().forEach(item -> {
                    if (item.error() != null) {
                        log.error("文档 {} 索引失败: {}", item.id(), item.error().reason());
                    }
                });
            }

        } catch (IOException e) {
            log.error("批量索引失败", e);
            throw new RuntimeException("批量索引失败", e);
        }
    }

    /**
     * 更新单个字段
     */
    @Override
    public void updateField(String id, String field, Object value) {
        try {
            UpdateResponse<Product> response = elasticsearchClient.update(
                    u -> u
                            .index("products")
                            .id(id)
                            .doc(Map.of(field, value)),
                    Product.class
            );

            log.info("更新成功，版本: {}", response.version());

        } catch (IOException e) {
            log.error("更新字段失败", e);
            throw new RuntimeException("更新字段失败", e);
        }
    }

    /**
     * 获取索引统计信息
     */
    @Override
    public Map<String, Object> getIndexStats() {
        try {
            IndicesStatsResponse response = elasticsearchClient.indices()
                    .stats(s -> s.index("products"));

            Map<String, Object> stats = new HashMap<>();
            stats.put("文档总数", response.indices().get("products").primaries().docs().count());
            stats.put("索引大小", response.indices().get("products").primaries().store().size());
            stats.put("分片数", response.indices().get("products").shards().size());

            return stats;

        } catch (IOException e) {
            log.error("获取索引统计失败", e);
            throw new RuntimeException("获取统计失败", e);
        }
    }

    @Override
    public Map<String, Aggregate> getAggregations() {
        try {
            SearchResponse<Void> response = elasticsearchClient.search(
                    s -> s
                            .index("products")
                            .size(0)
                            .aggregations("category_agg", a -> a
                                    .terms(t -> t.field("category.keyword"))
                            )
                            .aggregations("price_stats", a -> a
                                    .stats(st -> st.field("price"))
                            ),
                    Void.class
            );

            return response.aggregations();
        } catch (IOException e) {
            log.error("聚合查询失败", e);
//            return ResponseEntity.internalServerError().body("聚合查询失败: " + e.getMessage());
            throw new RuntimeException("聚合查询失败", e);
        }
    }

//          elasticsearchClient.search(s -> s
//            .index("products")
//            .query(q -> q.matchAll(m -> m.queryName("")))
//            .highlight(h -> h.fields("name").requireFieldMatch(false))
//            );

    @SneakyThrows
    @Override
    public List<Product> testHighlight(String keyword) {
        // 1. 构建搜索请求（流式 Builder 模式）
        SearchResponse<Product> response = elasticsearchClient.search(
                req -> req
                        .index("products") // 指定索引名
                        // 2.1 构建查询条件（match 查询）
                        .query(q -> q
                                .queryString(q2 -> q2
                                        .query(keyword)
                                )
                        )
                        // 2.2 构建高亮配置
                        .highlight(h -> h
                                .fields("name", f -> f // 高亮字段：name
                                        .requireFieldMatch(false) // 关闭字段匹配校验
                                )
                        ),
                Product.class // 指定响应反序列化的目标类型（也可先用Map接收，再手动解析）
        );

        // 3. 解析响应并返回结果
        return handleResponse(response);
    }

    /**
     * 解析 ES 8.x 高亮查询响应
     *
     * @param response ES 搜索响应
     * @return 处理后的 Product 列表
     */
    private List<Product> handleResponse(SearchResponse<Product> response) {
        // 1. 获取命中的结果元数据
        HitsMetadata<Product> hitsMetadata = response.hits();
        // 2. 获取总条数
        long total = hitsMetadata.total().value();
        System.out.println("共搜索到" + total + "条数据");

        // 3. 遍历命中的文档，处理高亮
        return hitsMetadata.hits().stream().map(hit -> {
                    // 3.1 获取原始文档数据（已自动反序列化为 Product/HotelDoc）
                    Product product = hit.source();
                    if (product == null) {
                        return null;
                    }

                    // 3.2 获取高亮字段结果
                    Map<String, List<String>> highlightFields = hit.highlight();
                    if (!CollectionUtils.isEmpty(highlightFields)) {
                        // 3.3 处理 name 字段的高亮结果
                        List<String> nameHighlights = highlightFields.get("name");
                        if (CollectionUtils.isNotEmpty(nameHighlights)) {
                            // 取第一个高亮片段，覆盖原字段值
                            String highlightName = nameHighlights.get(0);
                            product.setName(highlightName); // 假设 Product 有 setName 方法
                        }
                    }

                    System.out.println("product = " + product);
                    return product;
                }).filter(product -> product != null) // 过滤空值
                .collect(Collectors.toList());
    }
}