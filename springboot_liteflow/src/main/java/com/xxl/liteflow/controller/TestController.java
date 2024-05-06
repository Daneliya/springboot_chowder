package com.xxl.liteflow.controller;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xxl
 * @date 2024/5/6 23:04
 */
@RestController
public class TestController {

    @Resource
    private FlowExecutor flowExecutor;

    @PostMapping("/test")
    public void test() {
        LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
    }

}