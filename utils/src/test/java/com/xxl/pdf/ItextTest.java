package com.xxl.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.xxl.pdf.itext.Itext7Utils;
import gui.ava.html.parser.HtmlParser;
import gui.ava.html.parser.HtmlParserImpl;
import gui.ava.html.renderer.ImageRenderer;
import gui.ava.html.renderer.ImageRendererImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @Description: Apache POI和iText库进行Word转换为PDF
 * @Author: xxl
 * @Date: 2023/07/18 9:22
 * @Version: 1.0
 */
@Slf4j
public class ItextTest {

    @Test
    public void test() {
        String wordFilePath = "C:\\Users\\ek\\Desktop\\pdf测试\\临床教学规范.docx";
        String pdfFilePath = "C:\\Users\\ek\\Desktop\\pdf测试\\临床教学规范.pdf";
        try {
            // 读取Word文档
            XWPFDocument document = new XWPFDocument(new FileInputStream(wordFilePath));
//            HWPFDocument document = new HWPFDocument(new FileInputStream(wordFilePath));

            // 创建PDF文档
            Document pdfDocument = new Document();
            PdfWriter.getInstance(pdfDocument, new FileOutputStream(pdfFilePath));
            pdfDocument.open();

            // 将Word文档内容逐段写入PDF文档
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String text = paragraph.getText();
                pdfDocument.add(new Paragraph(text));
            }
            // 关闭文档
            pdfDocument.close();
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test02() throws IOException {
        long startTime = System.currentTimeMillis();
        // html文件所在相对路径
        String htmlFile = "src/main/resources/html/教学查房记录表_20250102134704.html";
        // pdf文件存储相对路径
        String pdfFile = "src/main/resources/x6.pdf";
        // 自定义水印
        String waterMarkText = "";
        InputStream inputStream = new FileInputStream(htmlFile);
        OutputStream outputStream = new FileOutputStream(pdfFile);
        // 微软雅黑在windows系统里的位置如下，linux系统直接拷贝该文件放在linux目录下即可
        // String fontPath = "src/main/resources/font/STHeiti Light.ttc,0";
//        String fontPath = "src/main/resources/font/simsun.ttc,0";
        String fontPath = "src/main/resources/fonts/msyh.ttc,0";
        Itext7Utils.convertToPdf(inputStream, waterMarkText, fontPath, outputStream);
        log.info("转换结束，耗时：{}ms", System.currentTimeMillis() - startTime);
    }

//    @Test
//    public void test03() throws IOException {
//        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
//        String htmlPath = "src/main/resources/html/教学查房记录表_20250102134704.html";
//        List<String> lines = Files.readAllLines(Paths.get(htmlPath));
//        lines.forEach(System.out::println);
//        String htmlTemplate = lines.toString();
//        imageGenerator.loadHtml(htmlTemplate);
//        imageGenerator.saveAsImage("D:\\hello-world.png");
//    }

    @Test
    public void test04() throws IOException {
        HtmlParser htmlParser = new HtmlParserImpl();
        String htmlPath = "src/main/resources/html/教学查房记录表_20250102134704.html";
        List<String> lines = Files.readAllLines(Paths.get(htmlPath));
        lines.forEach(System.out::println);
        String htmlTemplate = lines.toString();
        htmlParser.loadHtml(htmlTemplate);
        ImageRenderer imageRenderer = new ImageRendererImpl(htmlParser);
        imageRenderer.saveImage("D:\\hello-world.png");
    }
// wkhtmltox html 转 图片
// https://www.cnblogs.com/syui-terra/p/16820743.html
}
