package com.xxl.springboot_pdf.controller;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.xxl.springboot_pdf.util.DocxToPdfUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 * @author xxl
 * @date 2024/9/7 19:40
 */
@RestController
public class Documents4jController {

    @GetMapping("/test")
    public void test(HttpServletResponse response, MultipartFile file) throws Exception {
        IConverter converter = LocalConverter.builder().build();
        InputStream docxInputStream = file.getInputStream();
        OutputStream outputStream = response.getOutputStream();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"Generative.pdf\"");
        converter.convert(docxInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();
    }


    @GetMapping("/test2")
    public void test2(HttpServletResponse response, MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return;
        }

        String originalFilename = file.getOriginalFilename();
        String name = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        String filePath = "/home/springboot/springboot_pdf/file/";
//        File tempFile = File.createTempFile("temp", ".txt", new File(filePath));
//        File tempFile = File.createTempFile(filePath, "");
        File tempFile = File.createTempFile("temp", "", new File("/home/springboot/springboot_pdf/file/"));
//        File target = new File(filePath + name + ".pdf");

        IConverter converter = LocalConverter.builder().baseFolder(tempFile).workerPool(20, 25, 1, TimeUnit.SECONDS).processTimeout(5, TimeUnit.SECONDS).build();
        InputStream docxInputStream = file.getInputStream();
        OutputStream outputStream = response.getOutputStream();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"Generative.pdf\"");
        converter.convert(docxInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();
    }

    @GetMapping("/test3")
    public void test3(HttpServletResponse response) throws Exception {
        File file = DocxToPdfUtil.documents4jWordToPdf("/home/tools/Generative.docx");
        if (file != null && file.exists()) {
            // 设置响应头信息
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"Generative.pdf\"");

            // 读取文件内容并写入到输出流中
            try (FileInputStream fis = new FileInputStream(file);
                 OutputStream os = response.getOutputStream()) {
                IOUtils.copy(fis, os);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
        }
    }


//    public static void main(String[] args) {
//        IConverter converter = LocalConverter.builder().build();
//        File inputWord = new File("C:/Users/xxl/Desktop/Generative.docx");
//        File outputFile = new File("C:/Users/xxl/Desktop/Generative.pdf");
//        InputStream docxInputStream = new FileInputStream(inputWord);
//        OutputStream outputStream = new FileOutputStream(outputFile);
//        converter.convert(docxInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();
//    }


//    public static void main(String[] args) {
//        long startTime = System.currentTimeMillis();
//
//        wordToPdf("docx");
//
//        long endTime = System.currentTimeMillis();
//        long totalTime = endTime - startTime;
//        System.out.println("程序运行时间：" + totalTime + "毫秒");
//    }
//
//    /**
//     * 将之前对应的word文件转换成pdf，然后预览pdf文件
//     *
//     * @param suffix
//     */
//    public static void wordToPdf(String suffix) {
//        // 原始文档
//        File inputWord = new File("C:/Users/xxl/Desktop/Generative.docx");
//        // 转换之后的pdf文件
//        File outputFile = new File("C:/Users/xxl/Desktop/Generative.pdf");
//        try {
//            InputStream docxInputStream = new FileInputStream(inputWord);
//            OutputStream outputStream = new FileOutputStream(outputFile);
//            IConverter converter = LocalConverter.builder().build();
//            if (suffix.equals("doc")) {
//                converter.convert(docxInputStream).as(DocumentType.DOC).to(outputStream).as(DocumentType.PDF).execute();
//            } else if (suffix.equals("docx")) {
//                converter.convert(docxInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();
//            } else if (suffix.equals("txt")) {
//                converter.convert(docxInputStream).as(DocumentType.TEXT).to(outputStream).as(DocumentType.PDF).execute();
//            }
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
