package com.xxl.encrypt_hutool.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/05/31 14:04
 * @Version: 1.0
 */
@Data
@TableName("sys_person_phone_encrypt")
public class PersonPhoneEncrypt {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 关联人员信息表主键
     */
    @TableField("person_id")
    private Long personId;

    /**
     * 手机号码分词密文
     */
    @TableField("phone_key")
    private String phoneKey;

}
