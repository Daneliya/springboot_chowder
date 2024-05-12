package com.xxl.liteflow._0simple;

import com.xxl.liteflow.context.MyResponseContext;
import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;

/**
 * 转账失败组件
 *
 * @author xxl
 * @date 2024/5/12 0:18
 */
@Component("userTransferFail")
public class UserTransferFailComponent extends NodeComponent {

    @Override
    public void process() throws Exception {
        this.getContextBean(MyResponseContext.class).setSuccess(false);
    }

}
