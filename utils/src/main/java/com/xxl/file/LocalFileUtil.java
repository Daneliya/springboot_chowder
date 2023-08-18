package com.medical.hospital.common.oss;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Description: 本地文件上传
 * @Author: xxl
 * @Date: 2023/05/22 10:29
 * @Version: 1.0
 */
public class LocalFileUtil {

    private static String path = "/usr/local/file/data";

    public String uploadMultipartFile(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        // 保存文件到对应位置
        String filePath = path + "/" + originalFileName;
        File dir = new File(filePath);
        if (!dir.getParentFile().exists()) {
            dir.getParentFile().mkdirs();
        }
        try {
            // 在填写文件路径时，一定要是具体的文件名称（xx.txt），否则会出现拒绝访问
            file.transferTo(dir);
        } catch (IOException e) {
            //抛出异常
            e.printStackTrace();
            throw new MedicalHoPitalException("文件上传失败");
        }
        return filePath;
    }


}
