package com.xxl.elasticsearch.po;

import lombok.Data;

/**
 * @Description: 实体信息
 * @Author: xxl
 * @Date: 2023/03/18 10:01
 * @Version: 1.0
 */
@Data
public class Login {
    private Long userId;

    private Long mobile;

    private Integer tenantId;
}
