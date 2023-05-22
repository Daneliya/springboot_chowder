package com.xxl.minio.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * minio属性类
 *
 * @Author: xxl
 * @Date: 2022/09/14 16:52
 */
@Configuration
@Data
public class MinioProperties {
    /**
     * API调用地址
     */
    @Value("${minio.url}")
    private String url;
    /**
     * 连接账号
     */
    @Value("${minio.accesskey}")
    private String accessKey;
    /**
     * 连接秘钥
     */
    @Value("${minio.secretkey}")
    private String secretKey;
}
