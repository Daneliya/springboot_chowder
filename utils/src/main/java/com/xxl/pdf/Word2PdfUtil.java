package com.xxl.pdf;

import com.aspose.words.*;
import com.aspose.words.Shape;

import java.awt.*;

import java.io.*;

/**
 *         <dependency>
 *             <groupId>com.luhuiguo</groupId>
 *             <artifactId>aspose-words</artifactId>
 *             <version>22.4</version>
 *         </dependency>
 */

/**
 * word 转 pdf
 * https://www.cnblogs.com/name-lizonglin/p/12836451.html
 *
 * @Author: xxl
 * @Date: 2022/09/18 0:18
 */
public class Word2PdfUtil {

    public static void main(String[] args) {
        doc2pdf("C:/Users/ek/Desktop/a.doc", "C:/Users/ek/Desktop/test.pdf");
    }

    public static void doc2pdf(String inPath, String outPath) {
        FileOutputStream os = null;
        try {
            // 新建一个空白pdf文档
            File file = new File(outPath);
            os = new FileOutputStream(file);
            // Address是将要被转化的word文档
            Document doc = new Document(inPath);
            // insertWatermarkText(doc, "四叶草的诗雨");
            doc.save(os, SaveFormat.PDF);
            fileToByte(file);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 水印
     *
     * @param doc
     * @param watermarkText
     * @throws Exception
     */
    private static void insertWatermarkText(Document doc, String watermarkText) throws Exception {
        Shape watermark = new Shape(doc, ShapeType.TEXT_PLAIN_TEXT);
        //水印内容
        watermark.getTextPath().setText(watermarkText);
        //水印字体
        watermark.getTextPath().setFontFamily("宋体");
        //水印宽度
        watermark.setWidth(500);
        //水印高度
        watermark.setHeight(100);
        //旋转水印
        watermark.setRotation(-40);
        //水印颜色
        watermark.getFill().setColor(Color.lightGray);
        watermark.setStrokeColor(Color.lightGray);
        watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
        watermark.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);
        watermark.setWrapType(WrapType.NONE);
        watermark.setVerticalAlignment(VerticalAlignment.CENTER);
        watermark.setHorizontalAlignment(HorizontalAlignment.CENTER);
        Paragraph watermarkPara = new Paragraph(doc);
        watermarkPara.appendChild(watermark);
        for (Section sect : doc.getSections()) {
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_PRIMARY);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_FIRST);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_EVEN);
        }
        System.out.println("Watermark Set");
    }

    private static void insertWatermarkIntoHeader(Paragraph watermarkPara, Section sect, int headerType) throws Exception {
        HeaderFooter header = sect.getHeadersFooters().getByHeaderFooterType(headerType);
        if (header == null) {
            header = new HeaderFooter(sect.getDocument(), headerType);
            sect.getHeadersFooters().add(header);
        }
        header.appendChild(watermarkPara.deepClone(true));
    }

    /**
     * 将文件内容转成字节数组
     */
    public static byte[] fileToByte(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        ByteArrayOutputStream bAOutputStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bAOutputStream.write(ch);
        }
        byte data[] = bAOutputStream.toByteArray();
        bAOutputStream.close();
        return data;
    }
}
