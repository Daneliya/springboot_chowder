package com.xxl.elasticsearch.starter.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 产品实体类
 * 对应 Elasticsearch 中的 products 索引
 *
 * @author xxl
 * @date 2026/2/26 09:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // 告诉 Jackson 忽略未知的 _class 字段
@Document(indexName = "products", createIndex = true)
@Setting(settingPath = "elasticsearch/settings/ngram-setting.json")
public class Product {

    /**
     * 产品ID
     */
    @Id
    private String id;

    /**
     * 产品名称 - 支持全文搜索
     * FieldType.Text 类型会被分词
     * fields 定义多字段映射
     * - ik_max_word: 用于正常的语义搜索（搜"活动"）
     * - ngram: 用于数字或模糊匹配（搜"20"）
     */
    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword),
                    @InnerField(suffix = "pinyin", type = FieldType.Text, analyzer = "pinyin"),
                    @InnerField(suffix = "ngram", type = FieldType.Text, analyzer = "ngram")
            }
    )
    private String name;

    /**
     * 产品标题 - 简单文本字段
     */
    @Field(type = FieldType.Text, analyzer = "standard")
    private String title;

    /**
     * 产品描述 - 大文本字段
     * 支持拼音分词搜索
     */
    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword),
                    @InnerField(suffix = "pinyin", type = FieldType.Text, analyzer = "pinyin")
            }
    )
    private String description;

    /**
     * 价格 - 浮点型
     */
    @Field(type = FieldType.Double)
    private BigDecimal price;

    /**
     * 库存 - 整数型
     */
    @Field(type = FieldType.Integer)
    private Integer stock;

    /**
     * 分类 - 关键字类型，用于精确匹配
     */
    @Field(type = FieldType.Keyword)
    private String category;

    /**
     * 标签列表 - 数组类型
     */
    @Field(type = FieldType.Keyword)
    private List<String> tags;

    /**
     * 品牌 - 关键字类型
     */
    @Field(type = FieldType.Keyword)
    private String brand;

    /**
     * 创建时间 - 日期类型
     */
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private Date createTime;

    /**
     * 更新时间 - 日期类型
     */
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private Date updateTime;

    /**
     * 是否上架
     */
    @Field(type = FieldType.Boolean)
    private Boolean isActive;

    /**
     * 评分 - 嵌套对象类型
     */
    @Field(type = FieldType.Nested)
    private List<Rating> ratings;

    /**
     * 规格参数 - 对象类型
     */
    @Field(type = FieldType.Object)
    private Map<String, Object> specifications;

    /**
     * 地理位置信息
     */
    @GeoPointField
    private GeoPoint location;

    /**
     * 评分嵌套类
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Rating {
        @Field(type = FieldType.Text)
        private String username;

        @Field(type = FieldType.Integer)
        private Integer score;

        @Field(type = FieldType.Text)
        private String comment;

        @Field(type = FieldType.Date)
        private LocalDateTime ratingTime;
    }
}