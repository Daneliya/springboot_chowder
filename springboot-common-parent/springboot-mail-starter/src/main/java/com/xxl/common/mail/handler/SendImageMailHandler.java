package com.xxl.common.mail.handler;

import com.xxl.common.mail.constant.MailEnum;
import com.xxl.common.mail.factory.SendMailFactory;
import com.xxl.common.mail.factory.SendMailHandler;
import com.xxl.common.mail.model.MailProperties;
import com.xxl.common.mail.model.MailResult;
import com.xxl.common.mail.model.NotifyMailMessage;
import com.xxl.common.mail.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 携带图片的邮件
 *
 * @Author: xxl
 * @Date: 2024/11/27 10:28
 */
public class SendImageMailHandler extends SendMailHandler {

    @Autowired
    private MailProperties mailProperties;

    /**
     * 发送消息
     *
     * @param notifyMailMessage 消息体
     * @return 返回结果
     */
    @Override
    public MailResult sendMailMsg(NotifyMailMessage notifyMailMessage) {
        EmailUtil.sendEmail(mailProperties, notifyMailMessage.getEmailAddressee(), notifyMailMessage.getTitle(), notifyMailMessage.getContent(), null);
        return MailResult.ok();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        SendMailFactory.register(MailEnum.MAIL_IMAGE.name(), this);
    }
}
