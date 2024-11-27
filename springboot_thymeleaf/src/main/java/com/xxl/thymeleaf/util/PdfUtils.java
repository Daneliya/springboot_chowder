//package com.xxl.thymeleaf.util;
//
//import com.lowagie.text.DocumentException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.util.ObjectUtils;
//import org.springframework.util.StringUtils;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//import org.w3c.dom.Element;
//import org.xhtmlrenderer.pdf.ITextFontResolver;
//import org.xhtmlrenderer.pdf.ITextRenderer;
//
//import javax.lang.model.util.Elements;
//import javax.swing.text.Document;
//import java.io.*;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author: xxl
// * @Date: 2024/11/27 15:59
// */
//@Slf4j
//public class PdfUtils {
//
//    /**
//     * 字体路径
//     */
//    public static String FONT_PATH = "font/SIMSUN.TTC";
//    /**
//     * html模板路径
//     */
//    public static final String HTML_TEMPLATE_PATH = "templates/pdf/htmlTemplate.html";
//    /**
//     * 生成的pdf保存目录,这里是固定了目录，文件名需自己拼装
//     */
//    public static final String PDF_BASE_PATH = "src/main/resources/pdf/";
//    /**
//     * PDF扩展名
//     */
//    public static final String PDF_EXTENSION = ".pdf";
//    /**
//     * 图片基础URL
//     */
//    public static final String IMG_SAVE_URL = "http://117.78.37.58:8989/files/";
//    /**
//     * 图片保存路径
//     */
//    public static final String IMG_SAVE_PATH = "src/main/resources/img/";
//
//    public static TemplateEngine templateEngine = new TemplateEngine();
//
//    /**
//     * 读取文件
//     *
//     * @param file
//     * @return 读取文件的内容
//     */
//    public static String getFileString(File file) {
//        log.info("开始读取文件");
//        BufferedReader reader = null;
//        StringBuffer sb = new StringBuffer();
//        try {
//            reader = new BufferedReader(new FileReader(file));
//            String tempStr;
//            while ((tempStr = reader.readLine()) != null) {
//                sb.append(tempStr);
//            }
//            reader.close();
//            log.info("读取文件完成，文件信息：file={}", sb.toString());
//            return sb.toString();
//        } catch (IOException e) {
//            log.error("文件读取失败，cause--->" + e.getMessage());
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 根据模板生成html文件
//     *
//     * @param htmlTemplatePath html模板
//     * @param content          填充内容
//     * @throws IOException
//     * @return 根据模板生成的html
//     */
//    public static String getHtml(String htmlTemplatePath, String content) {
//        if (StringUtils.isEmpty(htmlTemplatePath)) {
//            htmlTemplatePath = HTML_TEMPLATE_PATH;
//        }
//        if (StringUtils.isEmpty(content)) {
//            log.debug("生成html失败：内容content为未空");
//            return null;
//        }
//        try {
//            log.info("开始是生成html文件");
//            Resource resource = new ClassPathResource(htmlTemplatePath);
//            File sourceFile = resource.getFile();
//            Context context = new Context();
//            // 将内容写入模板
//            Map<String, Object> params = new HashMap<>();
//            params.put("content", content);
//            context.setVariables(params);
//            return templateEngine.process(getFileString(sourceFile), context);
//        } catch (IOException e) {
//            log.error("html文件生成失败：cause--->" + e.getMessage());
//            return null;
//        }
//    }
//
//    /**
//     * 根据html生成PDF
//     *
//     * @param html html内容
//     * @param file 输出pdf文件的路径
//     * @throws DocumentException
//     * @throws IOException
//     */
//    public static void htmlToPdf(String html, File file) {
//        /**
//         * 切记 css 要定义在head 里，否则解析失败
//         * css 要定义字体
//         * 例如宋体style="font-family:SimSun"用simsun.ttc
//         */
//        if (!file.exists()) {
//            try {
//                if (file.getParentFile() != null && !file.getParentFile().exists()) {
//                    file.getParentFile().mkdirs();
//                }
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        log.info("开始根据html生成pdf,html={}", html);
//        OutputStream out = null;
//        try {
//            out = new FileOutputStream(file);
//            ITextRenderer renderer = new ITextRenderer();
//            // 携带图片,将图片标签转换为itext自己的图片对象
//            renderer.getSharedContext().setReplacedElementFactory(new Base64ImgReplacedElementFactory());
//            renderer.getSharedContext().getTextRenderer().setSmoothingThreshold(0);
//            // 解决中文支持问题
//            ITextFontResolver fontResolver = renderer.getFontResolver();
//            // 字体名称要大写，否则可能找不到
//            fontResolver.addFont(FONT_PATH, "Identity-H", false);
//            renderer.setDocumentFromString(html);
//            // 如果是本地图片使用 file：,这里指定图片的父级目录。html上写相对路径，
//            // renderer.getSharedContext().setBaseURL("file:/E:/img/")
//            // 处理图片
//            renderer.getSharedContext().setBaseURL(IMG_SAVE_URL);
//            renderer.layout();
//            renderer.createPDF(out);
//            out.flush();
//            log.info("pdf生成成功");
//        } catch (IOException e) {
//            log.error("pdf生成失败，cause--->" + e.getMessage());
//        } finally {
//            try {
//                if (null != out) {
//                    out.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 替换http图片url为其相对url
//     * 例如：http://117.78.37.58:8989/files/20200327\png\dcb09254b0d049d28550a9f31d8e88af.png
//     * 替换成：20200327/png/dcb09254b0d049d28550a9f31d8e88af.png
//     * 注意：一定要把 url中的所有 "\" 替换成 "/" 否者图片可能不会显示，原因不明
//     *
//     * @param html
//     * @return html字符串
//     */
//    public static String replaceImgTagSrc(String html) {
//        log.info("开始替换图片");
//        if (StringUtils.isEmpty(html)) {
//            log.debug("图片替换入参html为空");
//            return null;
//        }
//        // 解析html
//        Document document = Jsoup.parse(html);
//        Elements imgList = document.getElementsByTag("img");
//        if (ObjectUtils.isEmpty(imgList) || imgList.size() == 0) {
//            log.debug("html中没有图片需要替换");
//            return html;
//        }
//        List<String> srcList = new ArrayList();
//        for (Element img : imgList) {
//            // 获取src的值
//            String src = img.attr("src");
//            srcList.add(src);
//        }
//        log.info("html中img标签src值列表srcList={}", srcList);
////         遍历下载图片
////        List<String> imgPathList = downloadImg(srcList);
//        // 遍历获取图片相对路径
//        List<String> subImgUrlList = new ArrayList();
//        for (String imgUrl : srcList) {
//            // 我这里是用的http图片，所有图片都放在 files 下的，所以从 files/ 后面开始截取
//            // 获取图片相对路径，并把路径中的 "\" 替换成 "/"
//            String subImgUrl = imgUrl.substring(imgUrl.indexOf("files") + "files".length() + 1).replaceAll("\\\\", "/");
//            subImgUrlList.add(subImgUrl);
//        }
//        log.info("图片子路径列表subImgUrlList={}", subImgUrlList);
//        // 替换
//        for (int i = 0; i < imgList.size(); i++) {
//            imgList.get(i).attr("src", subImgUrlList.get(i));
//        }
//        log.info("图片替换完成后的html={}", document.toString());
//        return document.toString();
//    }
//
//    /**
//     * 批量下载图片
//     *
//     * @param imgUrlList 图片链接
//     * @return List<String> 本地存储路径列表
//     */
//    public static List<String> downloadImg(List<String> imgUrlList) {
//        try {
//            if (ObjectUtils.isEmpty(imgUrlList) || imgUrlList.size() == 0) {
//                log.info("图片路径列表入参不能为空");
//                return null;
//            }
//            List<String> imgPathList = new ArrayList();
//            for (String imgUrl : imgUrlList) {
//                if (!"".equals(imgUrl)) {
//                    String replaceImgUrl = "";
//                    if (imgUrl.contains("\\")) {
//                        replaceImgUrl = imgUrl.replaceAll("\\\\", "/");
//                    } else {
//                        replaceImgUrl = imgUrl;
//                    }
//                    String fileName = replaceImgUrl.substring(replaceImgUrl.lastIndexOf("/") + 1);
//                    String localImgPath = System.getProperty("user.dir") + "/" + IMG_SAVE_PATH + fileName;
//                    // 下载
//                    URL url = new URL(imgUrl);
//                    URLConnection connection = url.openConnection();
//                    InputStream is = connection.getInputStream();
//                    byte[] bs = new byte[1024];
//                    int len;
//                    File file = new File(localImgPath);
//                    // 图片不存在，下载图片
//                    if (!file.exists()) {
//                        try {
//                            if (file.getParentFile() != null && !file.getParentFile().exists()) {
//                                file.getParentFile().mkdirs();
//                            }
//                            file.createNewFile();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        FileOutputStream os = new FileOutputStream(file, true);
//                        while ((len = is.read(bs)) != -1) {
//                            os.write(bs, 0, len);
//                            os.flush();
//                        }
//                        os.close();
//                        is.close();
//                        imgPathList.add(localImgPath);
//                    } else {
//                        // 图片存在，直接使用
//                        imgPathList.add(localImgPath);
//                    }
//                } else {
//                    imgPathList.add(imgUrl);
//                }
//            }
//            return imgPathList;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//}
