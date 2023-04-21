package com.xxl.flex.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xxl
 * @date 2023/4/21 0:49
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "account")
public class Account implements Serializable {

    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String userName;

    private Integer age;

    private Date birthday;

}
