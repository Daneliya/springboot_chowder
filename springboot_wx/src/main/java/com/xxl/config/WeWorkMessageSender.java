//package com.xxl.config;
//
//import org.apache.logging.log4j.message.Message;
//
///**
// * @Description:
// * @Author: xxl
// * @Date: 2023/05/18 0:22
// * @Version: 1.0
// */
//public class WeWorkMessageSender {
//
//    private static final String TOUSER = "your_touser"; // 接收者用户ID(企业成员的ID)
//    private static final String API_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + ACCESS_TOKEN; // API请求地址
//
//    public static void sendTextMessage(String content) throws Exception {
//        Message message = new Message().setTouser(TOUSER).setMsgtype("text").setText(content); // 根据需求设置消息类型和内容
//        String response = HttpUtil.post(API_URL, JsonUtil.toJsonStr(message)); // 将消息转换为JSON格式并发送API请求
//        System.out.println("Send message to " + TOUSER + ": " + response); // 在控制台输出发送结果
//    }
//}
