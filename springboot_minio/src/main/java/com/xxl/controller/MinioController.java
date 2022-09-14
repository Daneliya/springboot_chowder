package com.xxl.controller;

import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @Author: xxl
 * @Date: 2022/09/14 16:59
 */
public class MinioController {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    /**
     * 文件上传
     *
     * @param file
     * @throws IOException
     * @throws XmlPullParserException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidArgumentException
     * @throws InvalidResponseException
     * @throws InternalException
     * @throws NoResponseException
     * @throws InvalidBucketNameException
     * @throws InsufficientDataException
     * @throws ErrorResponseException
     */
    @GetMapping(value = "/get/{bucketName}")
    public void upload(MultipartFile file, @PathVariable("bucketName") String bucketName) throws IOException, XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException, InvalidArgumentException, InvalidResponseException, InternalException, NoResponseException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException {
        if (file == null || file.getSize() == 0 || file.isEmpty()) {
            throw new RuntimeException("上传文件为空，请重新上传");
        }
        // 获取文件名
        String filename = file.getOriginalFilename();
        assert filename != null;
        /* 像下面这样写最主要是为了用来做分割，同时也保证文件名是唯一的 */
        String newFilename = UUID.randomUUID().toString() + "|" + filename + filename.substring(filename.lastIndexOf("."));
        minioClient.putObject(bucketName, newFilename, file.getInputStream(), file.getSize(), null, null, file.getContentType());
    }

    /**
     * 文件下载
     *
     * @param fileName
     * @param response
     * @return
     */
    public InputStream download(String fileName, HttpServletResponse response) {
        InputStream inputStream = null;
        // 根据文件名拿到minio中的文件对象
        try {
            ObjectStat object = minioClient.statObject(bucketName, fileName);
            // 设置响应头类型
            response.setContentType(object.contentType());
            inputStream = minioClient.getObject(bucketName, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * 查询全部文件
     *
     * @return
     */
    public Iterable<Result<Item>> listObjects() {
        try {
            return minioClient.listObjects(bucketName);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除指定文件
     *
     * @param fileName
     */
    public void deleteFile(String fileName) {
        try {
            minioClient.removeObject(bucketName, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
