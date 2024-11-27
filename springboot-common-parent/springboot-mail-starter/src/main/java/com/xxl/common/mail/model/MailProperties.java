package com.xxl.common.mail.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 邮件配置
 *
 * @Author: xxl
 * @Date: 2024/11/27 10:40
 */
@Data
@Component
@ConfigurationProperties(prefix = "xxl.mail")
public class MailProperties {

    /**
     * 邮件协议
     */
    private String emailProtocol;

    /**
     * 发件人的SMTP服务器地址
     */
    private String emailSMTPHost;

    /**
     * 端口（可选）
     */
    private String emailPort;

    /**
     * 发件人邮箱地址
     */
    private String emailAccount;

    /**
     * 发件人邮箱授权码
     */
    private String emailPassword;

}

