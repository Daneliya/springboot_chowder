package com.xxl.thymeleaf.controller;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xxl
 * @Date: 2024/11/27 17:03
 */
public class ActivityController {

    public static void main(String[] args) throws Exception {
        templateEngine();
    }

    public static void templateEngine() throws Exception {
        // 创建模板解析器
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/activity/");
        resolver.setSuffix(".html");

        // 创建模板引擎
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(resolver);

        // 创建上下文
        Context context = new Context();

        // 基本信息
        Map<String, String> activityInfo = new HashMap<>();
        activityInfo.put("theme", "急性心肌梗死疾病教学查房");
        activityInfo.put("activityTime", "2024-03-13 14:30");
        activityInfo.put("attendanceRate", "到课率:");
        activityInfo.put("teachingPlan", "是");
        activityInfo.put("supervisionStatus", "已督导");
        activityInfo.put("patientInfo", "张**（男，**床，56234155）临床诊断：急性右心衰时颈静脉怒张，心脏在短时间内扩 大等表现和急性心脏压塞相似，但后者一般不引 起肝脏肿大，且有明显的奇脉。");
        activityInfo.put("task", "汇报：刘子漫；签到：张可欣、王子睿、刘晓明；记录：郭小月、王大明；审核：张可欣；");
        activityInfo.put("note", "准备好查房所需的器械（检查推车或托盘），包括血压计、体温表、听诊器、手电筒、刻度尺、压舌板、棉签、笔、手消毒液等。实习生认真做好查房学习笔记。");
        context.setVariable("activityInfo", activityInfo);

        // 文件
        List<Object> teachingMaterialList = new ArrayList<>();
        Map<String, String> teachingMaterial = new HashMap<>();
        teachingMaterial.put("fileName", "急性心肌梗死疾病教学查房");
        teachingMaterial.put("type", "急性心肌梗死疾病教学查房");
        teachingMaterial.put("format", "急性心肌梗死疾病教学查房");
        teachingMaterial.put("uploadTime", "急性心肌梗死疾病教学查房");
        teachingMaterialList.add(teachingMaterial);
        Map<String, String> teachingMaterial2 = new HashMap<>();
        teachingMaterial2.put("fileName", "急性心肌梗死疾病教学查房2");
        teachingMaterial2.put("type", "急性心肌梗死疾病教学查房2");
        teachingMaterial2.put("format", "急性心肌梗死疾病教学查房2");
        teachingMaterial2.put("uploadTime", "急性心肌梗死疾病教学查房2");
        teachingMaterialList.add(teachingMaterial2);
        context.setVariable("teachingMaterialList", teachingMaterialList);

        // 生成 HTML 字符串
        String html = engine.process("activity.html", context);
        System.out.println(html);

        // 将 HTML 内容字符串转换为 PDF
        String outPath = "D:\\activity.pdf";
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
            engine.process("activity.html", context, writer);
        }
    }

    private static ConverterProperties initProperties() throws Exception {
        // 添加中文字体支持
        ConverterProperties properties = new ConverterProperties();
        FontProvider fontProvider = new FontProvider();
//        PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H");
//        PdfFont font = PdfFontFactory.createFont("src/main/resources/static/fonts/simsun.ttc", "UniGB-UCS2-H");
//        String path = "C:/WINDOWS/Fonts/微软雅黑.ttf";//windows里的字体资源路径
//        BaseFont font = BaseFont.createFont(path, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        //添加自定义字体，例如微软雅黑
//        String fontPath = "src/main/resources/static/fonts/simsun.ttc,0";
        String fontPath = "C:/WINDOWS/Fonts/simsun.ttc,0";
        PdfFont microsoft = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H);
        fontProvider.addFont(microsoft.getFontProgram(), PdfEncodings.IDENTITY_H);
        properties.setFontProvider(fontProvider);

        // 设置资源可访问地址，这点极为重要，Spring Boot 下 HTML 访问图片时，
        // 指定此路径后可以直接访问该路径下的图片文件
//        properties.setBaseUri("file:src/main/resources/static/images/");

        return properties;
    }
}
