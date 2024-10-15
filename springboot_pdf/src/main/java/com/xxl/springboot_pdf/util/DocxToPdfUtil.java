package com.xxl.springboot_pdf.util;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author xxl
 * @date 2024/9/9 0:46
 */

@Slf4j
public class DocxToPdfUtil {
    // windows 生成目录
    private static String winDirUrl = "C:\\Users\\xxl\\Documents\\";

    /**
     * 通过documents4j 实现word转pdf
     *
     * @param sourcePath 源文件地址 如 /root/example.doc
     */
    public static File documents4jWordToPdf(String sourcePath) {
        return documents4jWordToPdf(new File(sourcePath));
    }

    /**
     * 通过documents4j 实现word转pdf
     *
     * @param file 源文件
     */
    public static File documents4jWordToPdf(File file) {
        String os = System.getProperty("os.name").toLowerCase();

        log.info("当前系统：{}", os);
        if (os.contains("win")) {
            // Windows操作系统
            return winDocuments4jWordToPdf(file);
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            // Unix/Linux/Mac操作系统
            return linuxDocuments4jWordToPdf(file);
        } else {
            // 未知操作系统
            throw new RuntimeException("不支持当前操作系统转换文档");
        }
    }

    /**
     * 通过documents4j 实现word转pdf -- Windows 环境 需要有 Microsoft Office 服务
     *
     * @param file 源文件
     */
    public static File winDocuments4jWordToPdf(File file) {
        File outputFile = new File(winDirUrl + file.getName().replaceAll("\\.(docx?|\\w+)$", "") + ".pdf");
        try {
            // 这种方式在Linux服务器不可用，所以除非你是window服务器
            InputStream docxInputStream = new FileInputStream(file);
            OutputStream outputStream = new FileOutputStream(outputFile);
            IConverter converter = LocalConverter.builder().build();
            converter.convert(docxInputStream)
                    .as(DocumentType.DOCX)
                    .to(outputStream)
                    .as(DocumentType.PDF).execute();
            docxInputStream.close();
            outputStream.close();
            return outputFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过documents4j 实现word转pdf -- linux 环境 需要有 libreoffice 服务
     *
     * @param file 源文件
     */
    public static File linuxDocuments4jWordToPdf(File file) {
        // 获取文件的绝对路径和目录路径
        String absolutePath = file.getAbsolutePath();
        String parentPath = file.getParent();

        // 构建LibreOffice的命令行工具命令
        String commands = "libreoffice --convert-to pdf "
                + absolutePath + " --outdir " + parentPath;
        // 执行转换命令
        try {
            boolean result = executeLinuxCmd(commands);
            if (result) {
                // 转换成功，返回转换后的PDF文件
                String pdfFilePath = parentPath + File.separator + file.getName().replaceAll("\\.(docx?|\\w+)$", "") + ".pdf";
                log.info(pdfFilePath);
                log.info(pdfFilePath);
                return new File(pdfFilePath);
            } else {
                return null;
            }

        } catch (Exception e) {
            // 转换失败
            log.error("Word文档转换为PDF失败，原因：执行命令时出现异常。", e);
            return null;
        }
    }

    /**
     * 执行命令行
     *
     * @param cmd 命令行
     * @return
     * @throws IOException
     */
    private static boolean executeLinuxCmd(String cmd) throws IOException {
        // 执行命令行工具命令
        Process process = Runtime.getRuntime().exec(cmd);
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            log.error("执行 Linux 命令异常：", e);
            return false;
        }
        return true;
    }
}
