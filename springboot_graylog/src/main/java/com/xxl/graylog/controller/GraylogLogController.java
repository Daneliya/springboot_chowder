package com.xxl.graylog.controller;

import com.xxl.graylog.entity.GraylogLog;
import com.xxl.graylog.service.GraylogLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 记录日志内容 前端控制器
 * </p>
 *
 * @author xxl
 * @since 2025-06-23
 */
@RestController
@RequestMapping("/graylog")
public class GraylogLogController {

    @Autowired
    private GraylogLogService graylogLogService;

    /**
     * 查询测试数据
     */
    @GetMapping("/query")
    public Object query() {
        return graylogLogService.list();
    }

    /**
     * 保存测试数据
     */
    @GetMapping("/save")
    public Object save() {
        GraylogLog graylogLog = new GraylogLog();
        String jsonContent = String.format(
                "{ \"timestamp\": \"%s\", \"level\": \"INFO\", \"thread\": \"http-nio-8080-exec-1\", " +
                        "\"logger\": \"com.example.controller.TestController\", " +
                        "\"message\": \"Test log entry %s\" }",
                LocalDateTime.now(), UUID.randomUUID()
        );
        graylogLog.setContent(jsonContent);
        graylogLogService.save(graylogLog);
        return "保存成功";
    }
}

