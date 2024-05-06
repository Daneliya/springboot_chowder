package com.xxl.x_file_storage.controller;

import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 判断文件是否存在
 *
 * @Author: xxl
 * @Date: 2024/5/6 上午12:23
 */
@RestController
public class _3FileDetailExistController {


    /**
     * 注入实列
     */
    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 判断文件是否存在
     */
    @PostMapping("/exist1")
    public boolean exist1(@RequestParam("url") String url) {
        //获取文件信息
        FileInfo fileInfo = fileStorageService.getFileInfoByUrl(url);

        //判断文件是否存在
        return fileStorageService.exists(fileInfo);
    }

    /**
     * 判断文件是否存在
     */
    @PostMapping("/exist2")
    public boolean exist2(@RequestParam("url") String url) {
        //直接通过文件信息中的 url 判断文件是否存在，省去手动查询文件信息记录的过程
        return fileStorageService.exists(url);
    }
}
