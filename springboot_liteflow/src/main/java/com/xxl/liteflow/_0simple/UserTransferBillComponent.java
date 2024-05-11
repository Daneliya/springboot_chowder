package com.xxl.liteflow._0simple;

import com.xxl.liteflow.context.MyRequestContext;
import com.xxl.liteflow.context.MyResponseContext;
import com.xxl.liteflow.service.TransferBillService;
import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 转账账单组件
 *
 * @author xxl
 * @date 2024/5/11 0:01
 */
@Component("userTransferBill")
public class UserTransferBillComponent extends NodeComponent {

    @Autowired
    private TransferBillService transferBillService;

    @Override
    public void process() throws Exception {
        MyRequestContext requestContext = this.getContextBean(MyRequestContext.class);
        MyResponseContext responseContext = this.getContextBean(MyResponseContext.class);
        //记录账单
        transferBillService.recordBill(responseContext.getOrderId(), requestContext.getPayerId(), requestContext.getPayeeId());
    }
}