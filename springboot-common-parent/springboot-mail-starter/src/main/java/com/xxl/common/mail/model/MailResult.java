package com.xxl.common.mail.model;

import lombok.Data;

/**
 * 发送邮件结果实体
 *
 * @Author: xxl
 * @Date: 2024/11/27 13:20
 */
@Data
public class MailResult {

    /**
     * 响应状态（true成功、false失败）
     */
    private Boolean state;

    /**
     * 提示内容
     */
    private String msg;

    public MailResult() {
    }

    public static MailResult ok() {
        MailResult r = new MailResult();
        r.setState(true);
        r.setMsg("发送成功");
        return r;
    }

    public static MailResult error() {
        MailResult r = new MailResult();
        r.setState(true);
        r.setMsg("发送失败");
        return r;
    }

    public static MailResult error(String msg) {
        MailResult r = new MailResult();
        r.setState(true);
        r.setMsg(msg);
        return r;
    }
}