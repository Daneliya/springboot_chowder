package com.xxl.x_file_storage.controller;

import cn.hutool.core.io.file.FileNameUtil;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.core.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * 移动
 *
 * @Author: xxl
 * @Date: 2024/5/6 上午12:23
 */
@RestController
public class _5FileDetailMoveController {


    /**
     * 注入实列
     */
    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 移动
     */
    @PostMapping("/exist1")
    public boolean exist1(@RequestParam("url") String url) {
        // 上传源文件
        FileInfo fileInfo = fileStorageService.of(new File(url)).thumbnail().upload();

        // 移动到 move 这个路径下（同存储平台移动）
        FileInfo destFileInfo = fileStorageService.move(fileInfo)
                .setPath("move/")
                .move();

//        //移动到同路径下不同文件名（同存储平台移动）
//        FileInfo destFileInfo = fileStorageService.move(fileInfo)
//                .setFilename("aaaMove." + FileNameUtil.extName(fileInfo.getFilename()))
//                .setThFilename("aaaMove.min." + FileNameUtil.extName(fileInfo.getThFilename()))
//                .move();
//
//        //移动到其它存储平台（跨存储平台移动）
//        FileInfo destFileInfo = fileStorageService.move(fileInfo)
//                .setPlatform("local-plus-1")
//                .setProgressListener((progressSize, allSize) ->
//                        log.info("文件移动进度：{} {}%", progressSize, progressSize * 100 / allSize))
//                .move();
//
//        //强制使用跨存储平台移动
//        FileInfo destFileInfo = fileStorageService.move(fileInfo)
//                .setMoveMode(Constant.MoveMode.CROSS)
//                .setPath("move/")
//                .move();

        //是否支持同存储平台移动
        boolean supportSameMove = fileStorageService.isSupportSameMove("aliyun-oss-1");
        return supportSameMove;
    }
}
