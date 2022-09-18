package com.xxl.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.jsoup.Jsoup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * html 转 pdf
 * 原文：https://www.jb51.net/article/171362.htm
 * 重写 XMLWorker 处理中文不显示: https://blog.csdn.net/weixin_39981289/article/details/99568499
 *
 * @Author: xxl
 * @Date: 2022/09/16 10:59
 */
public class Html2PdfUtil {

    public static void main(String[] args) {

        String title = "标题";
        String content = "<p>标题</p><p><br></p><ol><li>当HashMap中的元素越来越多的时候，碰撞的几率也就越来越高（因为数组的长度是固定的），所以为了提高查询的效率，就要对HashMap的数组进行扩容，数组扩容这个操作也会出现在ArrayList中，所以这是一个通用的操作，很多人对它的性能表示过怀疑，不过想想我们的“均摊”原理，就释然了，而在hashmap数组扩容之后，最消耗性能的点就出现了：原数组中的数据必须重新计算其在新数组中的位置，并放进去，这就是resize。 </li><li>　　那么HashMap什么时候进行扩容呢？当hashmap中的元素个数超过数组大小*loadFactor时，就会进行数组扩容，loadFactor的默认值为0.75，也就是说，默认情况下，数组大小为16，那么当hashmap中元素个数超过16*0.75=12的时候，就把数组的大小扩展为2*16=32，即扩大一倍，然后重新计算每个元素在数组中的位置，而这是一个非常消耗性能的操作，所以如果我们已经预知hashmap中元素的个数，那么预设元素的个数能够有效的提高hashmap的性能。比如说，我们有1000个元素new HashMap(1000), 但是理论上来讲new HashMap(1024)更合适，不过上面annegu已经说过，即使是1000，hashmap也自动会将其设置为1024。 但是new HashMap(1024)还不是更合适的，因为0.75*1000 &lt; 1000, 也就是说为了让0.75 * size &gt; 1000, 我们必须这样new HashMap(2048)才最合适，既考虑了&amp;的问题，也避免了resize的问题。</li><li><br></li><li>值得提醒的是初始容量和负载因子也可以自己设定的。 使用的是位运算进行扩容，因为用乘法会影响CPU的性能，计算机不支持乘法运算，最终都会转化为加法运算。</li><li>————————————————</li><li>版权声明：本文为CSDN博主「Mr_yeml」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。</li><li>原文链接：https://blog.csdn.net/Mr_yeml/article/details/124040275</li></ol>";
        String pdfPath = "D:/iText-hello.pdf";
        htmlToPdf(title, content, pdfPath);
    }

    /**
     * 生成pdf工具类
     *
     * @return java.lang.Boolean
     * @Param [guideBook, pdfPath]
     */
    public static Boolean htmlToPdf(String title, String content, String pdfPath) {
        try {
            // 1.新建document
            Document document = new Document();
            // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
            //创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            // 3.打开文档
            document.open();
            //要解析的html
            //html转换成普通文字,方法如下:
            org.jsoup.nodes.Document contentDoc = Jsoup.parseBodyFragment(getHtml(title) + content);
            org.jsoup.nodes.Document.OutputSettings outputSettings = new org.jsoup.nodes.Document.OutputSettings();
            outputSettings.syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
            contentDoc.outputSettings(outputSettings);
            String parsedHtml = contentDoc.outerHtml();
            //这儿的font-family不支持汉字，{font-family:仿宋} 是不可以的。
            InputStream cssIs = new ByteArrayInputStream("* {font-family: PingFang-SC-Medium.otf;}".getBytes("UTF-8"));
            //第四个参数是html中的css文件的输入流
            //第五个参数是字体提供者，使用系统默认支持的字体时，可以不传。
            //XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(parsedHtml.getBytes()), cssIs);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(parsedHtml.getBytes()), null, Charset.defaultCharset(), new FontProviderUtil());
            // 5.关闭文档
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 下载文件
     *
     * @return void
     * @Param [request, response, inputStream, fileName]
     */
    public static void download(HttpServletRequest request, HttpServletResponse response, InputStream inputStream, String fileName) {
        BufferedOutputStream bos = null;
        try {
            // 定义输出缓冲 10k
            byte[] buffer = new byte[10240];
            //文件名称的处理
            // http://127.0.0.1:5002/guide-book/pdf?id=124
            fileName = fileName.replaceAll("[\\pP\\p{Punct}]", "-").replace(" ", "-").replaceAll("[-]+", "-") + ".pdf";
            String userAgent = request.getHeader("user-agent").toLowerCase();
            if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
            }
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            bos = new BufferedOutputStream(response.getOutputStream());
            int bytesRead = 0;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取html
     *
     * @return java.lang.String
     * @Param [title]
     */
    public static String getHtml(String title) {
        return "<h1 align=\"center\">" + title + "</h1>";
    }

}