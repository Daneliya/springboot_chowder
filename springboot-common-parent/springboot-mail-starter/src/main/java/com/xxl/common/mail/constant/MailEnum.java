package com.xxl.common.mail.constant;

import lombok.Getter;

/**
 * 邮件格式
 *
 * @Author: xxl
 * @Date: 2024/11/27 10:34
 */
@Getter
public enum MailEnum {

    MAIL_TEXT(1, "文本"),
    MAIL_IMAGE(2, "图片"),
    MAIL_HTML(2, "HTML"),
    MAIL_FILE(3, "附件");

    /**
     * 全参构造
     *
     * @param code 标识
     * @param msg  说明
     */
    MailEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 标识
     */
    private final int code;

    /**
     * 说明
     */
    private final String msg;

}
