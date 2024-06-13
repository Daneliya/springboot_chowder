package com.xxl.ocr.tess4j.test;

import com.xxl.ocr.tess4j.service.IOcrService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @Author: xxl
 * @Date: 2024/6/13 下午1:48
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Tess4JOcrTest {

    @Autowired
    private IOcrService iOcrService;

    @Test
    public void ocrLocalPng() {
        try {
            InputStream inputStream = new FileInputStream("D://SoftWare//Snipaste_2024-06-13_14-51-18.jpg");
            String result = iOcrService.recognizeText(inputStream);
            System.out.println("结果：" + result);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
