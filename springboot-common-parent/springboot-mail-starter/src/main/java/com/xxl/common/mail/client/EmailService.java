package com.xxl.common.mail.client;

import cn.hutool.core.util.ObjectUtil;
import com.xxl.common.mail.constant.MailEnum;
import com.xxl.common.mail.factory.SendMailFactory;
import com.xxl.common.mail.factory.SendMailHandler;
import com.xxl.common.mail.model.MailResult;
import com.xxl.common.mail.model.NotifyMailMessage;
import com.xxl.common.mail.util.EmailUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 发送邮件
 *
 * @Author: xxl
 * @Date: 2024/11/27 11:02
 */
public class EmailService {

    /**
     * 发送邮件
     *
     * @param notifyMailMessage 邮件内容
     * @return 返回结果
     */
    public MailResult sendMail(NotifyMailMessage notifyMailMessage) {
        // 校验邮件内容
        MailResult validMessage = validMailMessage(notifyMailMessage);
        if (!validMessage.getState()) {
            return validMessage;
        }
        // 根据文件内容确定发送哪种邮件
        String mailEnum = validMailPlatform(notifyMailMessage);
        if (mailEnum == null) {
            return MailResult.error("Mail Message Error, Unsupported message types！");
        }
        SendMailHandler invokeStrategy = SendMailFactory.getInvokeStrategy(mailEnum);
        return invokeStrategy.sendMailMsg(notifyMailMessage);
    }

    /**
     * 校验邮件内容
     *
     * @param notifyMailMessage 邮件内容
     * @return 返回结果
     */
    private MailResult validMailMessage(NotifyMailMessage notifyMailMessage) {
        if (ObjectUtil.isEmpty(notifyMailMessage.getTitle())) {
            return MailResult.error("标题不能为空");
        }
        if (ObjectUtil.isEmpty(notifyMailMessage.getEmailAddressee())) {
            return MailResult.error("收件人不能为空");
        }
        List<String> errorEmailList = new ArrayList<>();
        for (String email : notifyMailMessage.getEmailAddressee()) {
            boolean validEmail = EmailUtil.isValidEmail(email);
            if (!validEmail) {
                errorEmailList.add(email);
            }
        }
        if (!errorEmailList.isEmpty()) {
            return MailResult.error("收件人不能为空", errorEmailList);
        }
        return MailResult.ok();
    }

    /**
     * 根据文件内容确定发送哪种邮件
     *
     * @param notifyMailMessage 文件内容
     * @return 返回结果
     */
    public String validMailPlatform(NotifyMailMessage notifyMailMessage) {
        if (notifyMailMessage.getEmailFiles() != null) {
            return MailEnum.MAIL_FILE.name();
        } else if (notifyMailMessage.getEmailAttachment() != null) {
            return MailEnum.MAIL_FILE.name();
        }  else if (notifyMailMessage.getHtmlContent() != null) {
            return MailEnum.MAIL_HTML.name();
        } else if (notifyMailMessage.getImageUrl() != null) {
            return MailEnum.MAIL_IMAGE.name();
        } else if (notifyMailMessage.getContent() != null) {
            return MailEnum.MAIL_TEXT.name();
        }
        return null;
    }
}
