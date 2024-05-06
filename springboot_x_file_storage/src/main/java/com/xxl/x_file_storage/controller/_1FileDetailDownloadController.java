package com.xxl.x_file_storage.controller;

import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

/**
 * 下载
 *
 * @Author: xxl
 * @Date: 2024/5/6 上午12:16
 */
@RestController
public class _1FileDetailDownloadController {

    /**
     * 注入实列
     */
    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 下载
     */
    @PostMapping("/download")
    public void download(@RequestParam("url") String url) {
        // 获取文件信息
        FileInfo fileInfo = fileStorageService.getFileInfoByUrl(url);

        // 下载为字节数组
        byte[] bytes = fileStorageService.download(fileInfo).bytes();

        // 下载到文件
        fileStorageService.download(fileInfo).file("D:\\a.jpg");

        // 下载到 OutputStream 中
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        fileStorageService.download(fileInfo).outputStream(out);

        // 获取 InputStream 手动处理
        fileStorageService.download(fileInfo).inputStream(in -> {
            //TODO 读取 InputStream
        });

        // 直接通过文件信息中的 url 下载，省去手动查询文件信息记录的过程
        fileStorageService.download(url).file("D:\\a.jpg");

        // 下载缩略图
        fileStorageService.downloadTh(fileInfo).file("D:\\th.jpg");

    }
}
