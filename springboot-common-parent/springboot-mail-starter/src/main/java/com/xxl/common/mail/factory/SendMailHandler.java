package com.xxl.common.mail.factory;

import com.xxl.common.mail.model.MailResult;
import com.xxl.common.mail.model.NotifyMailMessage;
import org.springframework.beans.factory.InitializingBean;

/**
 * 邮件策略抽象
 *
 * @Author: xxl
 * @Date: 2024/11/27 10:31
 */
public abstract class SendMailHandler implements InitializingBean {

    /**
     * 发送消息
     *
     * @param notifyMailMessage 消息体
     * @return 返回结果
     */
    public MailResult sendMailMsg(NotifyMailMessage notifyMailMessage) {
        throw new UnsupportedOperationException();
    }

}
