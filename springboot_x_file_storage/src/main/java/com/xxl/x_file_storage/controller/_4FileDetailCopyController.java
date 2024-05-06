package com.xxl.x_file_storage.controller;

import cn.hutool.core.io.file.FileNameUtil;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.core.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * 复制
 *
 * @Author: xxl
 * @Date: 2024/5/6 上午12:23
 */
@Slf4j
@RestController
public class _4FileDetailCopyController {


    /**
     * 注入实列
     */
    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 复制
     */
    @PostMapping("/copy")
    public boolean copy(@RequestParam("url") String url) {
        // 上传源文件
        FileInfo fileInfo = fileStorageService.of(new File(url)).thumbnail().upload();

        // 复制到 copy 这个路径下（同存储平台复制）
        FileInfo destFileInfo = fileStorageService.copy(fileInfo)
                .setPath("copy/")
                .copy();

        //复制到同路径下不同文件名（同存储平台复制）
//        FileInfo destFileInfo = fileStorageService.copy(fileInfo)
//                .setFilename("aaaCopy." + FileNameUtil.extName(fileInfo.getFilename()))
//                .setThFilename("aaaCopy.min." + FileNameUtil.extName(fileInfo.getThFilename()))
//                .copy();
//
//        //复制到其它存储平台（跨存储平台复制）
//        FileInfo destFileInfo = fileStorageService.copy(fileInfo)
//                .setPlatform("local-plus-1")
//                .setProgressListener((progressSize, allSize) ->
//                        log.info("文件复制进度：{} {}%", progressSize, progressSize * 100 / allSize))
//                .copy();
//
//        //强制使用跨存储平台复制
//        FileInfo destFileInfo = fileStorageService.copy(fileInfo)
//                .setCopyMode(Constant.CopyMode.CROSS)
//                .setPath("copy/")
//                .copy();

        //是否支持同存储平台复制
        boolean supportSameCopy = fileStorageService.isSupportSameCopy("aliyun-oss-1");
        return supportSameCopy;
    }

}
