package com.xxl.thymeleaf.controller;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;
import com.xxl.thymeleaf.model.ImageInfo;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xxl
 * @Date: 2024/11/27 16:16
 */
public class PdfController {


    public static void main(String[] args) throws Exception {
        templateEngine();
    }

    public static void templateEngine() throws Exception {
        // 创建模板解析器
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/pdf/");
        resolver.setSuffix(".html");

        // 创建模板引擎
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(resolver);

        // 创建上下文
        Context context = new Context();

        // 假设 list 是一个包含图片信息的列表
        List<ImageInfo> list = new ArrayList<>();
        list.add(new ImageInfo("image1.jpg", "第一单元"));
        list.add(new ImageInfo("image2.jpg", "第二单元"));

        context.setVariable("images", list);

        // 生成 HTML 字符串
        String html = engine.process("one.html", context);
        System.out.println(html);

        // 将 HTML 内容字符串转换为 PDF
        String outPath = "D:\\11.pdf";
        OutputStream outputStream = new FileOutputStream(outPath);
        PdfWriter pdfWriter = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        HtmlConverter.convertToPdf(html, pdfDocument, initProperties());

        // 关闭资源
        pdfDocument.close();
        outputStream.close();

        // 生成文件到指定路径
        String htmlPath = "D:\\output.html";
        try (PrintWriter writer = new PrintWriter(new File(htmlPath))) {
            engine.process("one.html", context, writer);
        }
    }

    private static ConverterProperties initProperties() throws IOException {
        ConverterProperties properties = new ConverterProperties();
        FontProvider fontProvider = new FontProvider();
//        PdfFont font = PdfFontFactory.createFont("微软雅黑", "UniGB-UCS2-H");
//        PdfFont font = PdfFontFactory.createFont("src/main/resources/static/fonts/simsun.ttc", "UniGB-UCS2-H");
//        PdfFont font = PdfFontFactory.createFont("simsun.ttc", "UniGB-UCS2-H");
//        fontProvider.addFont(font.getFontProgram(), "UniGB-UCS2-H");
//        properties.setFontProvider(fontProvider);

        // 设置资源可访问地址，这点极为重要，Spring Boot 下 HTML 访问图片时，
        // 指定此路径后可以直接访问该路径下的图片文件
        properties.setBaseUri("file:src/main/resources/static/images/");

        return properties;
    }

//    public static void templateEngine() throws Exception {
//        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
//        resolver.setPrefix("templates/");
//        resolver.setSuffix(".html");
//        TemplateEngine engine = new TemplateEngine();
//        engine.setTemplateResolver(resolver);
//        Context context = new Context();
//
//        context.setVariable("title", "测试");
//        context.setVariable("images", list);
//        // 生成文件到指定路径
//        engine.process("模板名称", context, new PrintWriter(htmlPath));
//        // 生成字符串内容
//        String html = engine.process("模板名称", context);
//
//
//        // 将html 内容字符串 转换为 Pdf
//        String outPath = "D:\\11.pdf";
//        OutputStream outputStream = new FileOutputStream(outPath);
//        PdfWriter pdfWriter = new PdfWriter(outputStream);
//        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
//        // 设置为A4大小
//        pdfDocument.setDefaultPageSize(PageSize.A4);
//        HtmlConverter.convertToPdf(html, pdfDocument, initProperties());
//    }
//
//
//    private static ConverterProperties initProperties() throws IOException {
//        ConverterProperties properties = new ConverterProperties();
//        FontProvider fontProvider = new FontProvider();
//        PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H");
//        fontProvider.addFont(font.getFontProgram(),"UniGB-UCS2-H");
//        properties.setFontProvider(fontProvider);
//        // 设置资源可访问地址，这点极为重要，springboot 下hmtl访问图片时，
//        // 指定此路径后可以直接访问该路径下的图片文件
//        properties.setBaseUri("path");
//        return properties;
//    }

}
