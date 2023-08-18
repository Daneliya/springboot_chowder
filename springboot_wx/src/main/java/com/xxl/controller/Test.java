package com.xxl.controller;

import com.xxl.config.WxWorkSendMsg;

import java.io.IOException;
import java.util.Scanner;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/05/18 10:45
 * @Version: 1.0
 */
public class Test {

    public static void main(String[] args) throws IOException {
        String user = "1688858130460837";
        Scanner scan = new Scanner(System.in);
//        String content = scan.nextLine();
        String content = "你好呀~";
//        WxWorkSendMsg.sendTextMesg(user, content);
        WxWorkSendMsg.getAccessToken222();

        //WxWorkSendMsg.getToken("wwdd2095476f1aa7fb", "0tncpko_Mwqs2qj1iOhFKQSLgWwcv8oB6uxZigpwN7c");
        //String userInfo = WxWorkSendMsg.getUserInfo("");
    }


}
