package com.xxl.common.mail.handler;

import com.xxl.common.mail.constant.MailEnum;
import com.xxl.common.mail.factory.SendMailFactory;
import com.xxl.common.mail.factory.SendMailHandler;
import com.xxl.common.mail.model.MailProperties;
import com.xxl.common.mail.model.MailResult;
import com.xxl.common.mail.model.NotifyMailMessage;
import com.xxl.common.mail.util.EmailUtil;
import com.xxl.common.mail.util.NetUrlFileUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.File;

/**
 * 携带附件的邮件
 *
 * @Author: xxl
 * @Date: 2024/11/27 10:29
 */
//@RefreshScope
public class SendFileMailHandler extends SendMailHandler {

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

        try {
            // 邮件内容携带 附件 + （HTML内容+图片）使用 Content-Type:multipart/mixed
            // 1：设置一个多资源混合的邮件块 设置此类型时可以同时存在 附件和邮件内容  mixed代表混合
            MimeMultipart mixed = new MimeMultipart("mixed");
            // 2：创建附件资源
            int index = 1;
            for (String emailFile : notifyMailMessage.getEmailFiles()) {
                MimeBodyPart file_body = new MimeBodyPart();
                File netUrl = NetUrlFileUtil.getNetUrl(emailFile);
                String fileName = NetUrlFileUtil.getFileName(emailFile);
                DataHandler dhFile = new DataHandler(new FileDataSource(netUrl));
                file_body.setDataHandler(dhFile); //设置dhFile附件处理
                file_body.setContentID("file" + index);  //设置资源附件名称ID
                file_body.setFileName(MimeUtility.encodeText(fileName));   //设置中文附件名称
                mixed.addBodyPart(file_body);
                index++;
            }
            for (NotifyMailMessage.NotifyMailFile emailFile : notifyMailMessage.getEmailAttachment()) {
                MimeBodyPart file_body = new MimeBodyPart();
                File netUrl = NetUrlFileUtil.getNetUrl(emailFile.getFileUrl());
                String netFormat = NetUrlFileUtil.getFileFormat(emailFile.getFileUrl());
                DataHandler dhFile = new DataHandler(new FileDataSource(netUrl));
                file_body.setDataHandler(dhFile); //设置dhFile附件处理
                file_body.setContentID("file" + index);  //设置资源附件名称ID
                file_body.setFileName(MimeUtility.encodeText(emailFile.getFileName() + netFormat));   //设置中文附件名称
                mixed.addBodyPart(file_body);
                index++;
            }

            // 3：创建文本资源，文本资源并引用上面的图片ID（因为这两个资源我做了关联）
            MimeBodyPart text_body = new MimeBodyPart();
            text_body.setContent(EmailUtil.htmlTemplate(), "text/html;charset=UTF-8");

            // 4. 背景图部分
//        MimeBodyPart backgroundImagePart = new MimeBodyPart();
//        File backgroundImagePath = new File("D:\\msg.jpg");
//        backgroundImagePart.setDataHandler(new DataHandler(new FileDataSource(backgroundImagePath)));
//        backgroundImagePart.setContentID("backgroundImage");

            // 5：先把附件资源混合到 mixed多资源邮件模块里
            mixed.addBodyPart(text_body);
            EmailUtil.sendEmail(mailProperties, notifyMailMessage.getEmailAddressee(), notifyMailMessage.getTitle(), null, mixed);
            return MailResult.ok();
        } catch (Exception e) {
            return MailResult.error(e.getMessage());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        SendMailFactory.register(MailEnum.MAIL_FILE.name(), this);
    }
}
