package com.xxl.freemarker.util;


import freemarker.template.Template;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * @Author: xxl
 * @Date: 2024/11/22 11:14
 */
public class WordUtil {


//    LoggerFactory.get(WordUtil.class);

    //配置信息,代码本身写的还是很可读的,就不过多注解了
//    private static Configuration configuration = null;
    // 这里注意的是利用WordUtils的类加载器动态获得模板文件的位置

    //private static final String templateFolder = wordUtils.class.getClassLoader().getResource("../../../../templates").getPath();
//    private static final String templateFolder = WordUtil.class.getResource("D:" + File.separator + "guli_log").getPath();
//    Template template = freeMarkerConfigurer.getConfiguration().getTemplate("word.ftl");
//    static {
//        configuration = new Configuration();
//        configuration.setDefaultEncoding("utf-8");
//        try {
//            System.out.println(templateFolder);
//            configuration.setDirectoryForTemplateLoading(new File(templateFolder));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private WordUtil() {
        throw new AssertionError();
    }

    /**
     * 导出excel
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param map      word文档中参数
     * @param wordName 为模板的名字  例如xxx.ftl
     * @param fileName 是word 文件的名字 格式为："xxxx.doc"
     * @param name     是临时的文件夹米名称 string类型 可随意定义
     * @throws IOException
     */
    public static void exportMillCertificateWord(HttpServletRequest request, HttpServletResponse response,Template template, Map map, String wordName, String fileName, String name) throws IOException {
//        Template freemarkerTemplate = configuration.getTemplate(wordName);
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;
        try {
            // 调用工具类的createDoc方法生成Word文档
            file = createDoc(map, template, name);
            fin = new FileInputStream(file);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/x-download");
            fileName = new String(fileName.getBytes(), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(fileName)));
            out = response.getOutputStream();
            byte[] buffer = new byte[512];// 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fin != null) fin.close();
            if (out != null) out.close();
            if (file != null) file.delete();// 删除临时文件
        }
    }

    private static File createDoc(Map<?, ?> dataMap, Template template, String name) {
        File f = new File(name);
        Template t = template;
        try {
            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开
            Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
            t.process(dataMap, w);
            w.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return f;
    }

}
