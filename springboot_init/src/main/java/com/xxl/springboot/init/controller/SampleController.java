package com.xxl.springboot.init.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xxl
 * @date 2024/12/29 0:21
 */
@Controller
@EnableAutoConfiguration
public class SampleController {

    @RequestMapping("/2")
    @ResponseBody
    public String home() {
        return "www.xxl.cnnn";
    }

}
