package com.xxl.liteflow.context;

import lombok.Data;

/**
 * @author xxl
 * @date 2024/5/11 22:30
 */
@Data
public class MyRequestContext {

    /**
     * 付款人id
     */
    private String payerId;

    /**
     * 收款人id
     */
    private String payeeId;

    /**
     * 付款金额
     */
    private int money;
}
