package com.xxl.excel.result;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应体
 *
 * @author xxl
 * @date 2024/2/3 23:08
 */
@Data
@Builder
public class RestMessage implements Serializable {

    private Status status;
    private String message;
    private Object data;

    public RestMessage(Status status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
