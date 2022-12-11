package com.xxl.config;

import com.xxl.pojo.MinioProperties;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Author: xxl
 * @Date: 2022/09/14 16:55
 */
@Configuration
@Slf4j
public class MinioConfig {

    @Resource
    private MinioProperties minioProperties;

    @Bean
    public MinioClient getMinioClient() {
        try {
            return new MinioClient(minioProperties.getUrl(), minioProperties.getAccessKey(), minioProperties.getSecretKey());
        } catch (InvalidEndpointException | InvalidPortException e) {
            e.printStackTrace();
            log.info("-----创建Minio客户端失败-----");
            return null;
        }
    }
}
