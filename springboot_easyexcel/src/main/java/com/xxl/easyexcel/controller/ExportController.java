package com.xxl.easyexcel.controller;

import com.xxl.easyexcel.service.ExportService;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 导出测试
 *
 * @author xxl
 * @date 2024/5/17 0:19
 */
@RestController
public class ExportController {

    @Resource
    private ExportService exportService;

    @GetMapping("/export")
    public void export() throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        exportService.export();
        stopWatch.stop();
        System.out.println("共计耗时： " + stopWatch.getTotalTimeSeconds() + "S");
    }

}
