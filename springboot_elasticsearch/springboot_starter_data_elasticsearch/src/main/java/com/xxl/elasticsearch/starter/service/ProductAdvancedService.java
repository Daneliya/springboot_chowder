package com.xxl.elasticsearch.starter.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.IndicesStatsResponse;
import com.xxl.elasticsearch.starter.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**高级 Service
 * @author xxl
 * @date 2026/2/26 09:59
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductAdvancedService {

    private final ElasticsearchClient elasticsearchClient;

    /**
     * 使用ElasticsearchClient进行搜索
     */
    public List<Product> searchWithClient(String keyword) {
        try {
            SearchResponse<Product> response = elasticsearchClient.search(
                    s -> s
                            .index("products")
                            .query(q -> q
                                    .bool(b -> b
                                            .must(m -> m
                                                    .match(t -> t
                                                            .field("name")
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
}