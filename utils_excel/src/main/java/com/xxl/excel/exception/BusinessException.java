package com.xxl.excel.exception;

import lombok.Data;

/**
 * 全局异常类
 *
 * @author xxl
 * @date 2024/2/3 22:42
 */
@Data
public class BusinessException extends RuntimeException {

    private String code;
    private String message;

    public BusinessException(String message) {
        this.message = message;
    }
}
