package com.xxl.encrypt_hutool.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xxl.encrypt_hutool.anno.DecryptField;
import com.xxl.encrypt_hutool.anno.EncryptField;
import lombok.Data;


/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/05/31 14:04
 * @Version: 1.0
 */
@Data
@TableName("sys_person")
public class Person {

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 手机号
     */
    @EncryptField
    @DecryptField(open = true, start = 3, offset = 8)
    @TableField("phone_number")
    private String phoneNumber;

    /**
     * 用户名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 身份证件号码
     */
    @TableField("id_card")
    private String idCard;

    /**
     * 身份证件号码加密字段
     */
    @TableField("id_card_encrypted")
    private String idCardEncrypted;

}
