package com.xxl.liteflow.controller;

import com.xxl.liteflow.context.MyRequestContext;
import com.xxl.liteflow.context.MyResponseContext;
import com.yomahub.liteflow.core.FlowExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 转账流程调用接口
 *
 * @author xxl
 * @date 2024/5/11 22:14
 */
@RestController
public class UserTransferController {

    //LiteFlow核心bean，用来触发流程
    @Resource
    private FlowExecutor flowExecutor;

    @GetMapping(path = "/userTransferChainSimple")
    public String userTransferChainSimple(@RequestParam String payerId, @RequestParam String payeeId, @RequestParam int money) {
        //1. 构建请求上下文对象，用来传递参数进流程组件
        MyRequestContext requestContext = new MyRequestContext();
        requestContext.setPayerId(payerId);
        requestContext.setPayeeId(payeeId);
        requestContext.setMoney(money);

        //2. 调用流程引擎，传入请求上下文和结果上下文，结果上下文用于组件在其中设置各种结果
        MyResponseContext responseContext = new MyResponseContext();
//        flowExecutor.execute2Resp("userTransferChainSimple", null, requestContext, responseContext);
//        flowExecutor.execute2Resp("userTransferChainIfElse", null, requestContext, responseContext);
//        flowExecutor.execute2Resp("userTransferChainCatchException", null, requestContext, responseContext);
        flowExecutor.execute2Resp("userTransferChainFromDB", null, requestContext, responseContext);

        //3. 构造返回对象
        return buildResult(responseContext);
    }

    /**
     * 构造返回对象
     *
     * @param responseContext
     * @return
     */
    private String buildResult(MyResponseContext responseContext) {
        return "转账结果：" + responseContext.toString();
    }

    /**
     * 主动刷新缓存
     */
    @GetMapping(path = "/reloadLiteFlowConfigure")
    public void reloadLiteFlowConfigure() {
        flowExecutor.reloadRule();
    }
}