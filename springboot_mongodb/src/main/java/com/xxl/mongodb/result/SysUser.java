package com.xxl.mongodb.result;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author xxl
 * @date 2023/12/21 0:47
 */
@Data
@Document(collection = "sys_user")
public class SysUser {

    @Id
    private String id;

    private String userName;

    private String address;
}
