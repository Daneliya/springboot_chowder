package com.xxl.ocr.tess4j.service;

import java.io.InputStream;

/**
 * 接口层
 *
 * @Author: xxl
 * @Date: 2024/6/13 下午1:43
 */
public interface IOcrService {
    String recognizeText(InputStream sbs);
}