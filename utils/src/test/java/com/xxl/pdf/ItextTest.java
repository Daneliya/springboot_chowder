package com.xxl.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @Description: Apache POI和iText库进行Word转换为PDF
 * @Author: xxl
 * @Date: 2023/07/18 9:22
 * @Version: 1.0
 */
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
}
