package com.xxl.sse.service;

import com.xxl.sse.model.UploadStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author xxl
 * @date 2024/10/27 16:44
 */
@Service
public class FileUploadService {

    public void uploadFile(MultipartFile file, UploadStatus uploadStatus) throws IOException {
        // 创建一个表示上传目录的 Path 对象
        Path uploadPath = Paths.get("uploads");
        // 检查上传目录是否存在。如果不存在，则创建它
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try {
            // 创建表示上传文件最终位置的 Path 对象
            Path filePath = uploadPath.resolve(file.getOriginalFilename());
            // 将文件从输入流复制到指定路径
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not upload file: " + file.getOriginalFilename(), e);
        }
    }
}