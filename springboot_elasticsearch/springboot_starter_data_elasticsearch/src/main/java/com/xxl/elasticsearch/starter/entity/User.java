package com.xxl.elasticsearch.starter.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 用户实体类
 *
 * @author xxl
 * @date 2026/2/26 09:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "users")
public class User {

    @Id
    private String id;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word"),
            otherFields = @InnerField(suffix = "keyword", type = FieldType.Keyword)
    )
    private String username;

    @Field(type = FieldType.Keyword)
    private String email;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Keyword)
    private String gender;

    @Field(type = FieldType.Date, format = DateFormat.year_month_day)
    private LocalDate birthday;

    @Field(type = FieldType.Text)
    private String introduction;

    @Field(type = FieldType.Keyword)
    private List<String> interests;

    @Field(type = FieldType.Object)
    private Address address;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address {
        @Field(type = FieldType.Text)
        private String province;

        @Field(type = FieldType.Text)
        private String city;

        @Field(type = FieldType.Text)
        private String detail;

        @GeoPointField
        private GeoPoint location;
    }
}