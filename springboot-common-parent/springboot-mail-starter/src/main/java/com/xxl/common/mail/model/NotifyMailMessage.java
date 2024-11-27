package com.xxl.common.mail.model;

import lombok.Data;

import java.util.Set;

/**
 * 邮件请求体
 *
 * @Author: xxl
 * @Date: 2024/11/27 10:39
 */
@Data
public class NotifyMailMessage {

    /**
     * 邮件标题
     */
    private String title;

    /**
     * 收件人邮箱地址
     */
    private Set<String> emailAddressee;

    // =============文件内容===============

    /**
     * 纯文本内容
     */
    private String content;

    /**
     * 网页内容
     */
    private String htmlContent;

    /**
     * 邮件图片
     */
    private String imageUrl;

    /**
     * 邮件附件（使用默认文件名）
     */
    private Set<String> emailFiles;

    /**
     * 邮件附件（使用自定义文件名）
     */
    private Set<NotifyMailFile> emailAttachment;

    /**
     * 附件实体
     */
    @Data
    public static class NotifyMailFile {

        /**
         * 附件名称（无需添加后缀）
         */
        private String fileName;

        /**
         * 附件地址
         */
        private String fileUrl;
    }
}
