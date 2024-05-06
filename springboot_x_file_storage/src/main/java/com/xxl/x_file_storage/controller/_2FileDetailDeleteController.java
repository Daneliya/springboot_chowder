package com.xxl.x_file_storage.controller;

import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 删除
 *
 * @Author: xxl
 * @Date: 2024/5/6 上午12:23
 */
@RestController
public class _2FileDetailDeleteController {


    /**
     * 注入实列
     */
    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 直接删除
     */
    @PostMapping("/delete1")
    public boolean delete1(@RequestParam("url") String url) {
        //获取文件信息
        FileInfo fileInfo = fileStorageService.getFileInfoByUrl(url);
        //直接删除
        return fileStorageService.delete(fileInfo);
    }

    /**
     * 条件删除
     */
    @PostMapping("/delete2")
    public void delete2(@RequestParam("url") String url) {
        //获取文件信息
        FileInfo fileInfo = fileStorageService.getFileInfoByUrl(url);
        //条件删除
        fileStorageService.delete(fileInfo, info -> {
            //TODO 检查是否满足删除条件
            return true;
        });
    }

    /**
     * 直接通过文件信息中的 url 删除
     */
    @PostMapping("/delete3")
    public void delete3(@RequestParam("url") String url) {
        //直接通过文件信息中的 url 删除，省去手动查询文件信息记录的过程
        fileStorageService.delete(url);
    }
}
