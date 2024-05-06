package com.xxl.liteflow.component;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 执行组件链路
 *
 * @author xxl
 * @date 2024/5/6 22:50
 */
@Component
public class YourClass {

    @Resource
    private FlowExecutor flowExecutor;

    public void testConfig() {
        LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
    }
}
