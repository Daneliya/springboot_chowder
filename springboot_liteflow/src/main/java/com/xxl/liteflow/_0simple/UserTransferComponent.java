package com.xxl.liteflow._0simple;

import com.xxl.liteflow.context.MyRequestContext;
import com.xxl.liteflow.context.MyResponseContext;
import com.xxl.liteflow.service.TransferService;
import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 转账组件
 *
 * @author xxl
 * @date 2024/5/11 0:00
 */
@Component("userTransfer")
public class UserTransferComponent extends NodeComponent {

    @Autowired
    private TransferService transferService;

    @Override
    public void process() throws Exception {
        MyRequestContext requestContext = this.getContextBean(MyRequestContext.class);
        //执行转账
        String orderId = transferService.pay(requestContext.getPayerId(), requestContext.getPayeeId(), requestContext.getMoney());
        this.getContextBean(MyResponseContext.class).setOrderId(orderId);
    }
}