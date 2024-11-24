package com.xxl.springboot.mail.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xxl
 * @date 2024/11/24 20:38
 */
@RestController
@RequestMapping("/api/mail")
public class MailController {


    @GetMapping("/sendMail")
    public String sendMail() {

        return "执行成功";
    }
}
