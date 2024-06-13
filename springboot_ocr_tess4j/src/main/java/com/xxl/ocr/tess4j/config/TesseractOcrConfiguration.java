package com.xxl.ocr.tess4j.config;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，读取配置文件内容，并初始化Tesseract类，交给Spring管理
 *
 * @Author: xxl
 * @Date: 2024/6/13 上午11:20
 */
@Configuration
public class TesseractOcrConfiguration {

    @Value("${tess4j.datapath}")
    private String dataPath;

    @Bean
    public Tesseract tesseract() {

        Tesseract tesseract = new Tesseract();
        // 设置训练数据文件夹路径
        tesseract.setDatapath(dataPath);
        // 设置为中文简体
        tesseract.setLanguage("chi_sim");
        return tesseract;
    }
}

