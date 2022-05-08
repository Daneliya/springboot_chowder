package com.xxl.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author xxl
 * @date 2022/5/8 18:55
 */
@Data
@AllArgsConstructor
public class User {

    private Integer id;
    private String name;
    private Integer age;

}