package com.xxl.ocr.tess4j.service.impl;

import com.xxl.ocr.tess4j.service.IOcrService;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * 接口层实现层
 *
 * @Author: xxl
 * @Date: 2024/6/13 下午1:43
 */
@Service
public class OcrServiceImpl implements IOcrService {

    @Autowired
    private Tesseract tesseract;

    @Override
    public String recognizeText(InputStream sbs) {
        // 转换
        try {
            BufferedImage bufferedImage = ImageIO.read(sbs);
            // 对图片进行文字识别
            return tesseract.doOCR(bufferedImage);
        } catch (IOException | TesseractException e) {
            e.printStackTrace();
            return null;
        }
    }
}