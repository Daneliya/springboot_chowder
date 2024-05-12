package com.xxl.liteflow._0simple;

import com.xxl.liteflow.context.MyRequestContext;
import com.yomahub.liteflow.core.NodeBooleanComponent;
import org.springframework.stereotype.Component;

/**
 * 用户入参检查组件
 *
 * @author xxl
 * @date 2024/5/12 0:18
 */
@Component("userCheck")
public class UserCheckComponent extends NodeBooleanComponent {

    @Override
    public boolean processBoolean() throws Exception {
        MyRequestContext requestContext = this.getContextBean(MyRequestContext.class);
        int money = requestContext.getMoney();
        if (money <= 0) {
            return false;
        }
        return true;
    }
}
