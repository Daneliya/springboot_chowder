package com.xxl.repeat_submit.result;

import lombok.Getter;

/**
 * @author xxl
 * @date 2022/11/18 0:16
 */
@Getter
public enum ResResultEnum {
    DEFAULT_ERROR_MESSAGE("100", "系统繁忙"),
    SUBMIT_ERROR_MESSAGE("100", "系统繁忙"),
    GLOBAL_ERROR("101", "系统繁忙"),
    NOT_FOUND("404", "页面没有找到"),
    SUCCESS("200", "操作成功");

    private String code;
    private String message;

    ResResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
