package com.xxl.mybatis_plus.entity;

import lombok.Data;

/**
 * @author xxl
 * @date 2023/4/19 0:21
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}