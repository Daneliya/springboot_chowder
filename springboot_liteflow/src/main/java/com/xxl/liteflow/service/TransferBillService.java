package com.xxl.liteflow.service;

import org.springframework.stereotype.Service;

/**
 * @author xxl
 * @date 2024/5/11 22:36
 */
@Service
public class TransferBillService {

    /**
     * 记录账单
     *
     * @param orderId
     * @param payerId
     * @param payeeId
     */
    public void recordBill(String orderId, String payerId, String payeeId) {
        System.out.println("记录账单: orderId=" + orderId + ", payerId=" + payerId + ", payeeId=" + payeeId);
    }
}
