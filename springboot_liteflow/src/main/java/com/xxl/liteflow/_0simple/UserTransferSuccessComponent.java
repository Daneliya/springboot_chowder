package com.xxl.liteflow._0simple;

import com.xxl.liteflow.context.MyResponseContext;
import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;

/**
 * 转账成功组件
 *
 * @author xxl
 * @date 2024/5/11 0:01
 */
@Component("userTransferSuccess")
public class UserTransferSuccessComponent extends NodeComponent {

    @Override
    public void process() throws Exception {
        //设置转账成功标志
        this.getContextBean(MyResponseContext.class).setSuccess(true);
    }

}