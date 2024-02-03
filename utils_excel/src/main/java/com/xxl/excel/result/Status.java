package com.xxl.excel.result;

/**
 * 状态值
 *
 * @author xxl
 * @date 2024/2/3 23:08
 */
public enum Status {
    SUCCESS("操作成功"),
    ERROR("操作失败");

    private final String message;

    Status(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}  