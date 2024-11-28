package com.xxl.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xxl
 * @date 2024/11/28 21:57
 */
@Controller
public class StudyController {

    @GetMapping("/showHello")
    public String showHello(Model model) {
        model.addAttribute("msg", "Hello, Thymeleaf!");
        return "index";
    }

}
