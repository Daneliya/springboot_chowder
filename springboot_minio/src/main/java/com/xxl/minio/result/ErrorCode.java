package com.xxl.minio.result;

import lombok.Getter;
import org.springframework.util.ObjectUtils;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/05/22 9:02
 * @Version: 1.0
 */
@SuppressWarnings("all")
@Getter()
public enum ErrorCode {

    INTERNAL_SERVER_ERROR("内部服务器错误", "INTERNAL_SERVER_ERROR");

    private final String text;

    private final String value;

    ErrorCode(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static ErrorCode getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (ErrorCode anEnum : ErrorCode.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

}
