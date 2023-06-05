package com.xxl.caffeine.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户实体类
 *
 * @author xxl
 * @date 2023/6/6 0:17
 */
@Data
@AllArgsConstructor
public class User {

    private Integer id;
    private String uname;
    private String pwd;
    private Integer age;

}