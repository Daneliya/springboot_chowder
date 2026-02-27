package com.xxl.elasticsearch.starter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Elasticsearch 配置
 *
 * @author xxl
 * @date 2026/2/27 10:32
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.xxl.elasticsearch.starter.repository")
public class ElasticsearchConfig {

}