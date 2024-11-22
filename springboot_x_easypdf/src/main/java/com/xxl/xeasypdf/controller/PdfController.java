package com.xxl.xeasypdf.controller;

import org.dromara.pdf.pdfbox.component.barcode.XEasyPdfBarCode;
import org.dromara.pdf.pdfbox.component.image.XEasyPdfImageType;
import org.dromara.pdf.pdfbox.doc.XEasyPdfDefaultFontStyle;
import org.dromara.pdf.pdfbox.doc.XEasyPdfPositionStyle;
import org.dromara.pdf.pdfbox.handler.XEasyPdfHandler;
import org.dromara.pdf.pdfbox.mark.XEasyPdfDefaultWatermark;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Author: xxl
 * @Date: 2024/11/22 14:23
 */
@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @GetMapping("/getPdf")
    public void getPdf() throws IOException {
        ArrayList<Object> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            list.add("我是小孩也是王" + i);
        }
        // 定义pdf输出路径
        String outputPath = "D:\\test.pdf";
        // 构建文档
        XEasyPdfHandler.Document.build().setGlobalWatermark(new XEasyPdfDefaultWatermark("刘亦菲")).addPage(
                // 构建页面
                XEasyPdfHandler.Page.build(
                        // 构建组件
                        XEasyPdfHandler.BarCode.build(XEasyPdfBarCode.CodeType.QR_CODE, "xxx"),
                        //通过图片url地址写入图片
                        XEasyPdfHandler.Image.build(new URL("https://pics5.baidu.com/feed/c2cec3fdfc03924551e10f01283418c97c1e2590.jpeg").openStream(), XEasyPdfImageType.PNG),
                        //写入文本并设置颜色，水平居中，加粗
                        XEasyPdfHandler.Text.build("我是小孩也是王").setFontColor(new Color(255, 182, 193)).setHorizontalStyle(XEasyPdfPositionStyle.CENTER).setDefaultFontStyle(XEasyPdfDefaultFontStyle.BOLD),
                        XEasyPdfHandler.Text.build("xxx通知书").setHorizontalStyle(XEasyPdfPositionStyle.CENTER).setDefaultFontStyle(XEasyPdfDefaultFontStyle.BOLD),
                        //写入空行
                        XEasyPdfHandler.Text.build(Arrays.asList("")),
                        //写入文本列表，并设置左边距为200
                        XEasyPdfHandler.Text.build(String.valueOf(list)).setMarginLeft(200f)
                )
                // 保存文档到指定路径，并关闭
        ).save(outputPath).close();
    }
}
