package com.xxl.elasticsearch.dto;

import com.xxl.elasticsearch.po.Login;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 文档信息
 * @Author: xxl
 * @Date: 2023/03/18 10:04
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
public class LoginDoc {

    private Long userId;

    private Long mobile;

    private Integer tenantId;

    public LoginDoc(Login login) {
        this.userId = login.getUserId();
        this.mobile = login.getMobile();
        this.tenantId = login.getTenantId();
    }
}
