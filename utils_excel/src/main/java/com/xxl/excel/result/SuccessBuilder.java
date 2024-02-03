package com.xxl.excel.result;

/**
 * 成功响应
 *
 * @author xxl
 * @date 2024/2/4 0:27
 */
public class SuccessBuilder {

    private Object data;

    public SuccessBuilder data(Object data) {
        this.data = data;
        return this;
    }

    public RestMessage build() {
        return new RestMessage(Status.SUCCESS, "操作成功", data);
    }
}
