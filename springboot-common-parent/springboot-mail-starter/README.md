



## 1. 概述

本程序是用于发送不同类型邮件的工具，使用了 JavaMail API 来处理邮件发送的具体实现。通过工厂模式和策略模式，该工具能够根据需求动态选择不同的邮件发送策略，并且提供了一个统一的接口来简化邮件发送过程。

## 2. 主要组件

- **邮件发送策略接口 (SendMailHandler)**：定义了发送邮件的基本操作。
- **具体邮件发送策略 (SendTextMailHandler, SendImageMailHandler, SendHtmlMailHandler, SendFileMailHandler)**：实现了 `SendMailHandler` 接口，分别用于发送普通文本邮件、图片邮件、HTML 邮件和带附件的邮件。
- **邮件发送工厂 (SendMailFactory)**：根据配置或参数创建具体的邮件发送策略实例。
- **邮件发送服务 (EmailService)**：提供了发送邮件的统一接口，内部使用 `SendMailFactory` 创建具体的邮件发送策略并调用其方法来发送邮件。

## 3. 具体使用

引入依赖

~~~java
<dependency>
    <groupId>com.xxl</groupId>
    <artifactId>xxl-common-mail</artifactId>
    <version>1.0.0</version>
</dependency>
~~~

增加配置

~~~yaml
xxl:
  mail:
    emailProtocol: smtp
    emailSMTPHost: smtp.126.com
    emailAccount: xxx@126.com
    emailPassword: xxxxxxxx
~~~

使用示例

~~~java
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
~~~

