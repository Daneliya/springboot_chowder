package com.xxl.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 用户
 *
 * @author xxl
 * @date 2023/4/18 23:34
 */
@TableName("sys_user")
public class UserDO {

    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 邮箱
     */
    private String email;
}
