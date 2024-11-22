package com.xxl.freemarker.controller;

import com.xxl.freemarker.model.User;
import com.xxl.freemarker.util.WordUtil;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: xxl
 * @Date: 2024/11/22 11:19
 */
@RestController
@RequestMapping("/api/word")
public class WordController {

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;




//    public WordController() throws IOException {
//    }


    /**
     * 测试用户列表word的初级
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/getWord")
    public void getWord(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate("Freemarker测试文档.ftl");
        User user1 = new User();
        user1.setName("lisa");
        user1.setSex("girl");
        user1.setIphone("1243435512434");
        user1.setIdCard("4211821997909021");
        user1.setIdNum("12");
        User user2 = new User();
        user2.setName("tom");
        user2.setSex("boy");
        user2.setIphone("1243435512434");
        user2.setIdCard("4211821997909021");
        user2.setIdNum("12");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        //HashMap<String, List<User>> map1 = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("users", users);
        map.put("appName", "用户信息");
        String wordName = "Freemarker测试文档.ftl";
        String fileName = "Freemarker输出文档.doc";
        String name = "name.doc";

        WordUtil.exportMillCertificateWord(request, response, template, map, wordName, fileName, name);
    }
}