package com.xxl.shardingjdbc.entity;

import lombok.Data;

/**
 * @Description: 用户表
 * @Author: xxl
 * @Date: 2023/02/10 17:23
 * @Version: 1.0
 */
@Data
public class User {
    // 主键
    private Integer id;
    // 昵称
    private String nickname;
    // 密码
    private String password;
    // 性
    private Integer sex;
    // 性
    private String birthday;
}