package com.xxl.springboot.init.controller;

import com.xxl.springboot.init.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author xxl
 * @date 2024/12/30 22:32
 */
@RestController
public class MessageController {

    @RequestMapping("/")
    public String home() {
        return "www.xxl.cn";
    }

    @GetMapping("/echo")
    public String echo(String msg) {
        return "【echo】" + msg;
    }

    @GetMapping("/echo/{message}")
    public String echo2(@PathVariable("message") String msg) {
        return "【echo】" + msg;
    }

    @GetMapping("/object")
    public Object object(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        map.put("客户端IP地址", request.getRemoteAddr());
        map.put("客户端响应编码", request.getCharacterEncoding());
        map.put("SessionID", request.getSession().getId());
        map.put("项目真实路径", request.getServletContext().getRealPath("/"));
        return map;
    }

    @GetMapping("/object2")
    public Object object2() {
        // 获取 HttpServletRequest 内置对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 获取 HttpServletResponse 内置对象
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        Map<String, String> map = new HashMap<>();
        map.put("客户端IP地址", request.getRemoteAddr());
        map.put("客户端响应编码", request.getCharacterEncoding());
        map.put("SessionID", request.getSession().getId());
        map.put("项目真实路径", request.getServletContext().getRealPath("/"));
        return map;
    }

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/message")
    public Object message() {
        Map<String, String> map = new HashMap<>();
        map.put("welcome.url", this.messageSource.getMessage("welcome.url", null, Locale.getDefault()));
        map.put("welcome.msg", this.messageSource.getMessage("welcome.msg", new Object[]{"卡皮巴拉"}, Locale.getDefault()));
        return map;
    }

    @GetMapping("/i18n")
    public Object i18n(String type) {
        Map<String, String> map = new HashMap<>();
        map.put("welcome.url", this.messageSource.getMessage("welcome.url", null, Locale.getDefault()));
        if (type != null && type.equals("en")) {
            map.put("welcome.msg", this.messageSource.getMessage("welcome.msg", new Object[]{"卡皮巴拉"}, new Locale("en", "US")));
            return map;
        }
        map.put("welcome.msg", this.messageSource.getMessage("welcome.msg", new Object[]{"卡皮巴拉"}, new Locale("zh", "CN")));
        return map;
    }

    @Autowired
    private MessageUtil messageUtil; // XML配置注入

    @GetMapping("/info")
    public Object info() {
        // 调用方法
        return this.messageUtil.getInfo();
    }
}
