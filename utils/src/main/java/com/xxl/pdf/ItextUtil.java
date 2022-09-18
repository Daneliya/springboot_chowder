package com.xxl.pdf;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

/**
 *         <dependency>
 *             <groupId>org.xhtmlrenderer</groupId>
 *             <artifactId>flying-saucer-pdf</artifactId>
 *             <version>9.1.22</version>
 *         </dependency>
 */

/**
 * @Author: xxl
 * @Date: 2022/09/16 11:07
 */
public class ItextUtil {

    public static void main(String[] args) throws Exception {
        String pdfPath = "D://iText-hello.pdf";
        createPdf(pdfPath);
    }


    public static void createPdf(String filename) throws DocumentException, IOException {
        // 设置大小
        Rectangle pageSize = new Rectangle(300, 500);
        // 设置背景颜色
        pageSize.setBackgroundColor(new BaseColor(0xFF, 0xFF, 0xDE));
        // 设置边框颜色
        pageSize.setBorderColor(new BaseColor(0xFF, 0xFF, 0xDE));


        // step 1：创建Document对象
        Document document = new Document(pageSize);
        // step 2：获取PdfWriter实例
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3：打开Document
        document.open();

        String text = Html2TextUtil.getContent("<p>标题</p><p><br></p><ol><li>当HashMap中的元素越来越多的时候，碰撞的几率也就越来越高（因为数组的长度是固定的），所以为了提高查询的效率，就要对HashMap的数组进行扩容，数组扩容这个操作也会出现在ArrayList中，所以这是一个通用的操作，很多人对它的性能表示过怀疑，不过想想我们的“均摊”原理，就释然了，而在hashmap数组扩容之后，最消耗性能的点就出现了：原数组中的数据必须重新计算其在新数组中的位置，并放进去，这就是resize。 </li><li>　　那么HashMap什么时候进行扩容呢？当hashmap中的元素个数超过数组大小*loadFactor时，就会进行数组扩容，loadFactor的默认值为0.75，也就是说，默认情况下，数组大小为16，那么当hashmap中元素个数超过16*0.75=12的时候，就把数组的大小扩展为2*16=32，即扩大一倍，然后重新计算每个元素在数组中的位置，而这是一个非常消耗性能的操作，所以如果我们已经预知hashmap中元素的个数，那么预设元素的个数能够有效的提高hashmap的性能。比如说，我们有1000个元素new HashMap(1000), 但是理论上来讲new HashMap(1024)更合适，不过上面annegu已经说过，即使是1000，hashmap也自动会将其设置为1024。 但是new HashMap(1024)还不是更合适的，因为0.75*1000 &lt; 1000, 也就是说为了让0.75 * size &gt; 1000, 我们必须这样new HashMap(2048)才最合适，既考虑了&amp;的问题，也避免了resize的问题。</li><li><br></li><li>值得提醒的是初始容量和负载因子也可以自己设定的。 使用的是位运算进行扩容，因为用乘法会影响CPU的性能，计算机不支持乘法运算，最终都会转化为加法运算。</li><li>————————————————</li><li>版权声明：本文为CSDN博主「Mr_yeml」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。</li><li>原文链接：https://blog.csdn.net/Mr_yeml/article/details/124040275</li></ol>");

        // step 4：添加内容
        document.add(new Paragraph(text));
        // step 5：关闭打开的Document
        document.close();
    }


    /**
     * A4
     */
    public static String getConversionHtmlCode(String htmlCode, String size) {
        String css = "";
        css += "<style>";
        css += "@page{size:" + size + "}";
        css += "</style>";
        String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\">" +
                "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" + css + "</head>" + htmlCode + "</html>";
        // System.out.println("html:"+html);
        return html;
    }

    public static void htmlCodeComeString(String htmlCode, HttpServletResponse response, String size, String name) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name + ".pdf", "UTF-8"));
        response.setHeader("Cache-Control", "no-cache");
        ServletOutputStream os = response.getOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(getConversionHtmlCode(htmlCode, size));
        ITextFontResolver fontResolver = renderer.getFontResolver();
        // 中文黑体字
        URL songti = ItextUtil.class.getResource("songti.ttf");
        URL fontPath = ItextUtil.class.getResource("simsun.ttc");
        fontResolver.addFont(fontPath.toString(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        fontResolver.addFont(songti.toString(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.getSharedContext().setBaseURL("file:/" + "D:/PET-logo.png");
        renderer.layout();
        renderer.createPDF(os);

        System.out.println("======转换成功!");
        os.close();
        os.flush();
    }


    /**
     * 转义特殊字符
     * @param args
     */
    /**
     * 替换一个字符串中的某些指定字符
     *
     * @param strData     String 原始字符串
     * @param regex       String 要替换的字符串
     * @param replacement String 替代字符串
     * @return String 替换后的字符串
     */
    public static String replaceString(String strData, String regex, String replacement) {
        if (strData == null) {
            return null;
        }
        int index;
        index = strData.indexOf(regex);
        String strNew = "";
        if (index >= 0) {
            while (index >= 0) {
                strNew += strData.substring(0, index) + replacement;
                strData = strData.substring(index + regex.length());
                index = strData.indexOf(regex);
            }
            strNew += strData;
            return strNew;
        }
        return strData;
    }

    /**
     * 替换字符串中特殊字符
     */
    public static String encodeString(String strData) {
        if (strData == null) {
            return "";
        }
        strData = replaceString(strData, "&", "&amp;");
        strData = replaceString(strData, "<", "&lt;");
        strData = replaceString(strData, ">", "&gt;");
        strData = replaceString(strData, "&apos;", "&apos;");
        strData = replaceString(strData, "\"", "&quot;");
        return strData;
    }

    /**
     * 还原字符串中特殊字符
     */
    public static String decodeString(String strData) {
        strData = replaceString(strData, "&lt;", "<");
        strData = replaceString(strData, "&gt;", ">");
        strData = replaceString(strData, "&apos;", "&apos;");
        strData = replaceString(strData, "&quot;", "\"");
        strData = replaceString(strData, "&amp;", "&");
        return strData;
    }
}
