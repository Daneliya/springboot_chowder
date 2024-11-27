package com.xxl.mail.controller;

import com.xxl.common.mail.client.EmailService;
import com.xxl.common.mail.model.MailResult;
import com.xxl.common.mail.model.NotifyMailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: xxl
 * @Date: 2024/11/27 15:00
 */
@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @RequestMapping("/sendEmail")
    public MailResult sendEmail() {
        NotifyMailMessage notifyMailMessage = new NotifyMailMessage();
        // 标题
        notifyMailMessage.setTitle("标题");
        // 附件：使用默认名称
        Set<String> fileSet = new HashSet<>();
        fileSet.add("https://oss.lcsxs.com/hebei/shijiazhuang/ykyxfsdyyy6746b2f7ebfc534a53fc2e07.pdf");
        fileSet.add("https://oss.lcsxs.com/shandong/jinan/ykyxfstasrmyy/icon1732086945847.png");
        notifyMailMessage.setEmailFiles(fileSet);
        // 附件：使用自定义名称
        Set<NotifyMailMessage.NotifyMailFile> attachmentSet = new HashSet<>();
        NotifyMailMessage.NotifyMailFile notifyMailFile = new NotifyMailMessage.NotifyMailFile();
        notifyMailFile.setFileName("Generative Agents Interactive Simulacra of Human Behavior");
        notifyMailFile.setFileUrl("https://oss.lcsxs.com/hebei/shijiazhuang/ykyxfsdyyy6746b2f7ebfc534a53fc2e07.pdf");
        attachmentSet.add(notifyMailFile);
        notifyMailMessage.setEmailAttachment(attachmentSet);
        // 收件人
        Set<String> addresseeSet = new HashSet<>();
        addresseeSet.add("xxxxx@126.com");
        notifyMailMessage.setEmailAddressee(addresseeSet);
        // 发送邮件
        return emailService.sendMail(notifyMailMessage);
    }
}
