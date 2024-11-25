package com.xxl.springboot.mail.test;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author xxl
 * @date 2024/11/24 20:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaxJavaMailClient {

    // 邮件协议
    private static final String emailProtocol = "smtp";
    // 发件人的SMTP服务器地址（企业邮箱）
    private static final String emailSMTPHost = "smtp.126.com";
    // 端口
    private static final String emailPort = "465";
    // 发件人邮箱地址
    private static final String emailAccount = "luckily126163@126.com"; // 这个是企业邮箱
    // 发件人邮箱授权码
    private static final String emailPassword = "HArk935Q9yEuCvnr";
    // 收件人邮箱地址
//    private static final String emailAddressee = "508686616@qq.com";
    private static final String emailAddressee = "xuxiaolong@88.com";
//    private static final String emailAddressee = "luckily126163@126.com";

    @Test
    public void testText() throws Exception {
        Set<String> set = new HashSet<>();
        set.add(emailAddressee);
        // 发送普通文本
        System.out.println(sendEmail(set, "测试发送邮件的接口！", "您好！这是我发送的一封测试发送接口的邮件，看完请删除记录。", null));
    }

    @Test
    public void testHtml() throws Exception {
        Set<String> set = new HashSet<>();
        set.add(emailAddressee);
        // 发送html
        String htmlContent = "<h1>XXL祝大家工作顺利！</h1><font color=\"#FF0000\">万事顺心</font>";
        System.out.println(sendEmail(set, "测试发送邮件的接口！", htmlContent, null));
    }

    @Test
    public void testHtmlImage() throws Exception {
        Set<String> set = new HashSet<>();
        set.add(emailAddressee);
        // 发送html携带图片邮件格式
        MimeMultipart mimeMultipart = generateImage();
        System.out.println(sendEmail(set, "测试发送邮件的接口！", null, mimeMultipart));
    }

    @Test
    public void testHtmlFile() throws Exception {
        Set<String> set = new HashSet<>();
        set.add(emailAddressee);
        // 发送html携带附件
        MimeMultipart mimeMultipart = generateFile();
        System.out.println(sendEmail(set, "测试发送邮件的接口！", null, mimeMultipart));
    }

    @Test
    public void testHtmlTemplate() throws Exception {
        Set<String> set = new HashSet<>();
        set.add(emailAddressee);
        // 发送html模板
        System.out.println(sendEmail(set, "测试发送邮件的接口！", htmlTemplate(), null));
    }

    private MimeMultipart generateFile() throws Exception {
        // 邮件内容携带 附件 + （HTML内容+图片）使用Content-Type:multipart/mixed
        // 5：设置一个多资源混合的邮件块 设置此类型时可以同时存在 附件和邮件内容  mixed代表混合
        MimeMultipart mixed = new MimeMultipart("mixed");

        // 5.1：创建一个附件资源
        MimeBodyPart file_body = new MimeBodyPart();
//        DataHandler dhFile = new DataHandler(JavaxJavaMailClient.class.getResource("static/Generative Agents Interactive Simulacra of Human Behavior.pdf"));
        File file0 = new File("D:\\Generative Agents Interactive Simulacra of Human Behavior.pdf");
        DataHandler dhFile = new DataHandler(new FileDataSource(file0));
        file_body.setDataHandler(dhFile); //设置dhFile附件处理
        file_body.setContentID("fileA");  //设置资源附件名称ID
        //file_body.setFileName("拉拉.zip");   //设置中文附件名称（未处理编码）
        file_body.setFileName(MimeUtility.encodeText("论文.pdf"));   //设置中文附件名称

        // 3：创建文本资源，文本资源并引用上面的图片ID（因为这两个资源我做了关联）
        MimeBodyPart text_body = new MimeBodyPart();
        text_body.setContent(htmlTemplate(), "text/html;charset=UTF-8");


        // 9. 背景图部分
        MimeBodyPart backgroundImagePart = new MimeBodyPart();
        File backgroundImagePath = new File("D:\\msg.jpg");
        backgroundImagePart.setDataHandler(new DataHandler(new FileDataSource(backgroundImagePath)));
        backgroundImagePart.setContentID("backgroundImage");

        // 5.2：先把附件资源混合到 mixed多资源邮件模块里
        mixed.addBodyPart(text_body);
        mixed.addBodyPart(file_body);
        return mixed;
        // 5.3：创建主体内容资源存储对象
//        MimeBodyPart content = new MimeBodyPart();
//        // 把主体内容混合到资源存储对象里
//        mixed.addBodyPart(content);
//        // 5.4：设置多资源内容
//        //=============== 构建邮件内容：多信息片段邮件 使用Content-Type:multipart/related ===============//
//        // 5.4.1：构建一个多资源的邮件块 用来把 文本内容资源 和 图片资源合并；；；related代表多资源关联
//        MimeMultipart text_img_related = new MimeMultipart("related");
//        //text_img_related.setSubType("related");
//        //注：这里为什么填写related的请去查阅Multipart类型或者去文章开头跳转我之前上一篇邮件介绍
//        // 5.4.2：把关联的把多资源邮件块 混合到mixed多资源邮件模块里
//        content.setContent(text_img_related);
//
//        // 5.4.3：创建图片资源
//        MimeBodyPart img_body = new MimeBodyPart();
////        DataHandler dhImg = new DataHandler(JavaxJavaMailClient.class.getResource("static/msg.png"));
//        File file = new File("D:\\msg.jpg");
//        DataHandler dhImg = new DataHandler(new FileDataSource(file));
//        img_body.setDataHandler(dhImg); //设置dhImg图片处理
//        img_body.setContentID("imgA");  //设置资源图片名称ID
//
//        // 5.4.4：创建文本资源，文本资源并引用上面的图片ID（因为这两个资源我做了关联）
//        MimeBodyPart text_body = new MimeBodyPart();
//        text_body.setContent("<img src='cid:imgA' width=100/> 我是XXL！！", "text/html;charset=UTF-8");
//
//        // 5.4.5：把创建出来的两个资源合并到多资源模块了
//        text_img_related.addBodyPart(img_body);
//        text_img_related.addBodyPart(text_body);
//        return text_img_related;
    }

    @SneakyThrows
    private MimeMultipart generateImage() throws MessagingException {
        // 设置多资源内容
        //=============== 构建邮件内容：多信息片段关联邮件 使用Content-Type:multipart/related ===============//
        // 1：构建一个多资源的邮件块 用来把 文本内容资源 和 图片资源关联；；；related代表多资源关联
        MimeMultipart text_img_related = new MimeMultipart("related");
        //text_img_related.setSubType("related");
        //注：这里为什么填写related的请去查阅Multipart类型或者去文章开头跳转我之前上一篇邮件介绍
        // 2：创建图片资源
        MimeBodyPart img_body = new MimeBodyPart();
        File file = new File("D:\\msg.jpg");
//        DataHandler dhImg = new DataHandler(new FileInputStream(file), "image/jpg");
        DataHandler dhImg = new DataHandler(new FileDataSource(file));
//        DataHandler dhImg = new DataHandler(JavaxJavaMailClient.class.getResource("static/msg.jpg"));
        img_body.setDataHandler(dhImg); //设置dhImg图片处理
        img_body.setContentID("imgA");  //设置资源图片名称ID

        // 3：创建文本资源，文本资源并引用上面的图片ID（因为这两个资源我做了关联）
        MimeBodyPart text_body = new MimeBodyPart();
        text_body.setContent("<img src='cid:imgA' width=100/> 我是XXL！！", "text/html;charset=UTF-8");

        // 4：把创建出来的两个资源合并到多资源模块了
        text_img_related.addBodyPart(img_body);
        text_img_related.addBodyPart(text_body);
        return text_img_related;
    }

    /**
     * 发送邮件
     *
     * @param emails  目标邮件地址
     * @param title   邮件标题
     * @param content 邮件内容
     */
    public boolean sendEmail(Set<String> emails, String title, String content, MimeMultipart mimeBodyPart) {
        // 未传收件人邮箱地址则直接返回
        if (emails == null || emails.isEmpty()) {
            return false;
        }
        try {
            // 1. 创建参数配置, 用于连接邮件服务器的参数配置
            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", emailProtocol); // 使用的协议（JavaMail规范要求）
            props.setProperty("mail.smtp.host", emailSMTPHost); // 指定smtp服务器地址
//            props.setProperty("mail.smtp.port", emailPort); // 指定smtp端口号
            // 使用smtp身份验证
            props.setProperty("mail.smtp.auth", "true"); // 需要请求认证
            props.put("mail.smtp.ssl.enable", "true"); // 开启SSL
            props.put("mail.smtp.ssl.protocols", "TLSv1.2"); // 指定SSL版本
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            // 由于Properties默认不限制请求时间，可能会导致线程阻塞，所以指定请求时长
            props.setProperty("mail.smtp.connectiontimeout", "10000");// 与邮件服务器建立连接的时间限制
            props.setProperty("mail.smtp.timeout", "10000");// 邮件smtp读取的时间限制
            props.setProperty("mail.smtp.writetimeout", "10000");// 邮件内容上传的时间限制
            // 2. 根据配置创建会话对象, 用于和邮件服务器交互
            Session session = Session.getDefaultInstance(props);
            session.setDebug(true); // 设置为debug模式, 可以查看详细的发送log
            // 3. 创建邮件
            MimeMessage message = new MimeMessage(session);
            // 4. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
            message.setFrom(new InternetAddress(emailAccount, "刘亦菲", "UTF-8"));
            // 5. To: 收件人（可以增加多个收件人、抄送、密送）
            // MimeMessage.RecipientType.TO: 发送 MimeMessage.RecipientType.CC：抄送 MimeMessage.RecipientType.BCC：密送
            int size = emails.size();
            // 单个目标邮箱还是多个
            if (size == 1) {
                String email = emails.iterator().next();
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(email, email, "UTF-8"));
            } else {
                InternetAddress[] addresses = new InternetAddress[emails.size()];
                int i = 0;
                for (String email : emails) {
                    addresses[i++] = new InternetAddress(email, email, "UTF-8");
                }
                message.setRecipients(MimeMessage.RecipientType.TO, addresses);
            }
            // 6. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
            message.setSubject(title, "UTF-8");
            // 7. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
            if (content != null) {
                message.setContent(content, "text/html;charset=UTF-8");
            } else if (mimeBodyPart != null) {
//                message.setContent(mimeBodyPart, "text/html;charset=UTF-8");
                message.setContent(mimeBodyPart);
            } else {
                throw new IllegalArgumentException("Both content and mimeBodyPart are null");
            }
            // 8. 设置发件时间
            message.setSentDate(new Date());
            // 9. 保存设置
            message.saveChanges();
            // 10. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();
            transport.connect(emailAccount, emailPassword);
            // 11. 发送邮件, 发到所有的收件地址, message.getAllRecipients()获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
            // 12. 关闭传输连接
            transport.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String htmlTemplate() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"zh-CN\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>邮件标题</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            line-height: 1.6;\n" +
//                "            color: #F0F3FA;\n" +
                "            color: #333;\n" +
                "            background-color: #f9f9f9;\n" +
//                "            background-image: url('cid:backgroundImage');\n" +
//                "            background-image: url('D:\\msg.jpg);\n" +
//                "            background-size: cover;\n" +
//                "            background-repeat: no-repeat;\n" +
//                "            background-position: center center;\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            background-color: #fff;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        h1 {\n" +
                "            color: #007BFF;\n" +
                "            font-size: 24px;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        p {\n" +
                "            margin-bottom: 15px;\n" +
                "        }\n" +
                "        .button {\n" +
                "            display: inline-block;\n" +
                "            background-color: #007BFF;\n" +
                "            color: #fff;\n" +
                "            text-decoration: none;\n" +
                "            padding: 10px 20px;\n" +
                "            border-radius: 5px;\n" +
                "            transition: background-color 0.3s ease;\n" +
                "        }\n" +
                "        .button:hover {\n" +
                "            background-color: #0056b3;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>尊敬的用户您好！</h1>\n" +
                "        <p>感谢您使用我们的服务。附件是您请求的PDF文件。</p>\n" +
//                "        <p>感谢您使用我们的服务。以下是您请求的PDF文件。</p>\n" +
//                "        <p>请点击下方按钮下载文件：</p>\n" +
//                "        <a href=\"cid:pdfAttachment\" class=\"button\">下载PDF文件</a>\n" +
                "        <p>如有任何问题或建议，请随时联系我们。</p>\n" +
                "        <p>祝您生活愉快！</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }

}

