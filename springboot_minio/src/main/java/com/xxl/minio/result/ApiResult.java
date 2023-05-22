package com.xxl.minio.result;

import lombok.Data;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/05/22 8:48
 * @Version: 1.0
 */
@Data
public class ApiResult<T> {

    private int code;

    private T data;

    private String message;

    public ApiResult(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public ApiResult(int code, T data) {
        this(code, data, "");
    }

    public ApiResult(int code, String message) {
        this(code, (T) "", message);
    }


    /**
     * 成功信息
     *
     * @param data
     * @return
     */
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(0, data, "ok");
    }

    /**
     * 错误信息
     *
     * @param code
     * @param message
     * @return
     */
    public static ApiResult error(int code, String message) {
        return new ApiResult(code, message);
    }

    public static ApiResult error(String message) {
        return new ApiResult(1, message);
    }

    public static ApiResult error(int code) {
        return new ApiResult(code, ErrorCode.INTERNAL_SERVER_ERROR.getText());
    }
}
