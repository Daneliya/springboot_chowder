package com.xxl.sse.controller;

import cn.hutool.core.util.IdUtil;
import com.xxl.sse.config.SseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @Author: xxl
 * @Date: 2024/5/29 上午10:29
 */
@RestController
public class IndexAction {

    @Autowired
    private SseClient sseClient;

    @GetMapping("/")
    public String index(ModelMap model) {
        String uid = IdUtil.fastUUID();
        model.put("uid", uid);
        return "index";
    }

    @CrossOrigin
    @GetMapping("/createSse")
    public SseEmitter createConnect(String uid) {
        return sseClient.createSse(uid);
    }

    @CrossOrigin
    @GetMapping("/sendMsg")
    @ResponseBody
    public String sseChat(String uid) {
        for (int i = 0; i < 10; i++) {
            sseClient.sendMessage(uid, "no" + i, IdUtil.fastUUID());
        }
        return "ok";
    }

    /**
     * 关闭连接
     */
    @CrossOrigin
    @GetMapping("/closeSse")
    public void closeConnect(String uid) {
        sseClient.closeSse(uid);
    }
}
