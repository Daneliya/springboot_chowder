package com.xxl.thymeleaf.controller.util;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author xxl
 * @date 2024/11/29 0:06
 */
public class PdfUtils {

    /**
     * HTML 转换成 PDF
     *
     * @param html
     * @throws Exception
     */
    public static void convertToPdf(String html) throws Exception {
        // 将 HTML 内容字符串转换为 PDF
        String outPath = "D:\\activity2.pdf";
        OutputStream outputStream = new FileOutputStream(outPath);
        PdfWriter pdfWriter = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        HtmlConverter.convertToPdf(html, pdfDocument, initProperties());
        // 关闭资源
        pdfDocument.close();
        outputStream.close();
    }

    /**
     * pdf 特殊处理
     *
     * @return
     * @throws Exception
     */
    private static ConverterProperties initProperties() throws Exception {
        // 添加中文字体支持
        ConverterProperties properties = new ConverterProperties();
        FontProvider fontProvider = new FontProvider();
        String fontPath = "C:/WINDOWS/Fonts/simsun.ttc,0";
        PdfFont microsoft = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H);
        fontProvider.addFont(microsoft.getFontProgram(), PdfEncodings.IDENTITY_H);
        properties.setFontProvider(fontProvider);
        return properties;
    }
}
