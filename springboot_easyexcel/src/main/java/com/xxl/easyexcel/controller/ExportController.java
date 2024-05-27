package com.xxl.easyexcel.controller;

import com.alibaba.excel.EasyExcel;
import com.xxl.easyexcel.entity.ExportExcelEntity;
import com.xxl.easyexcel.listener.DemoDataListener;
import com.xxl.easyexcel.service.ExportService;
import com.xxl.easyexcel.utils.FakerUtil;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * 导出测试
 *
 * @author xxl
 * @date 2024/5/17 0:19
 */
@RestController
@RequestMapping("/excel")
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

    /**
     * 上传、导入
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), ExportExcelEntity.class, new DemoDataListener()).sheet().doRead();
        // 如果配置了数据持久层
        //EasyExcel.read(file.getInputStream(), ExportExcelEntity.class, new DemoDataListener(demoService)).sheet().doRead();
        return "success";
    }

    /**
     * 导出指定列、忽略列
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("download/exclude")
    public void downloadExcludeColumn(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("测试", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 根据用户传入字段，假设我们要忽略 userName
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("userName");
        // 忽略某些列，使其不导出 excludeColumnFiledNames
        EasyExcel.write(response.getOutputStream(), ExportExcelEntity.class).excludeColumnFiledNames(excludeColumnFiledNames).sheet("模板").doWrite(FakerUtil.generateStudentList(1000));

        // 根据用户传入字段，例如只导出userName和mobile列
        Set<String> includeColumnFiledNames = new HashSet<>();
        includeColumnFiledNames.add("userName");
        includeColumnFiledNames.add("mobile");
        // 只导出选中列 includeColumnFiledNames
        EasyExcel.write(response.getOutputStream(), ExportExcelEntity.class).includeColumnFiledNames(includeColumnFiledNames).sheet("模板").doWrite(FakerUtil.generateStudentList(1000));
    }
}
