package com.xxl.liteflow.service;

import org.springframework.stereotype.Service;

/**
 * @author xxl
 * @date 2024/5/11 22:38
 */
@Service
public class TransferService {

    /**
     * 支付
     *
     * @param payerId
     * @param payeeId
     * @param money
     * @return
     */
    public String pay(String payerId, String payeeId, int money) {
        return "success";
    }
}
