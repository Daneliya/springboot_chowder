package com.xxl.sse.controller;

import com.xxl.sse.model.UploadStatus;
import com.xxl.sse.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xxl
 * @date 2024/10/27 16:44
 */
@CrossOrigin(origins = "*")
@RestController
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @PostMapping(value = "/upload", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        SseEmitter emitter = new SseEmitter();
        UploadStatus uploadStatus = new UploadStatus();
        uploadStatus.setContentLength(file.getSize());
        // 使用线程池异步执行文件上传任务
        executorService.execute(() -> {
            try {
                // 文件上传
                fileUploadService.uploadFile(file, uploadStatus);
                uploadStatus.setBytesRead(file.getSize());
                // 发送上传状态到客户端
                emitter.send(SseEmitter.event().data(uploadStatus));
                // 标记SSE事件流完成
                emitter.complete();
            } catch (Exception e) {
                // 通知客户端错误
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

}
