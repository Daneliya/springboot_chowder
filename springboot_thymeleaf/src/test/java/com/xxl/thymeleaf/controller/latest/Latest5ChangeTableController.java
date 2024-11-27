package com.xxl.thymeleaf.controller.latest;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: xxl
 * @Date: 2024/11/27 18:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Latest5ChangeTableController {

    @Resource
    private TemplateEngine templateEngine;

    @Test
    public void test() throws Exception {
        List<AppInfo> appInfoList1 = new ArrayList<>();
        AppInfo build1 = AppInfo.builder().id(111L).appId(111L).name("name1").price("price1").language("language1").version("version1").url("url").createTime(new Date()).updateTime(new Date()).build();
        AppInfo build2 = AppInfo.builder().id(111L).appId(111L).name("name1").price("price1").language("language1").version("version1").url("url").createTime(new Date()).updateTime(new Date()).build();
        appInfoList1.add(build1);
        appInfoList1.add(build2);
        Map<String, List<AppInfo>> upPriceMap = new HashMap<>();
        upPriceMap.put("upPriceMap1", appInfoList1);
        upPriceMap.put("upPriceMap2", appInfoList1);

        List<AppInfo> appInfoList2 = new ArrayList<>();
        AppInfo build3 = AppInfo.builder().id(111L).appId(111L).name("name1").price("price1").language("language1").version("version1").url("url").createTime(new Date()).updateTime(new Date()).build();
        AppInfo build4 = AppInfo.builder().id(111L).appId(111L).name("name1").price("price1").language("language1").version("version1").url("url").createTime(new Date()).updateTime(new Date()).build();
        appInfoList2.add(build3);
        appInfoList2.add(build4);
        Map<String, List<AppInfo>> downPriceMap = new HashMap<>();
        downPriceMap.put("downPriceMap1", appInfoList2);
        downPriceMap.put("downPriceMap2", appInfoList2);

        AppPriceInfoVO build = AppPriceInfoVO.builder().upNum(1).downNum(2).mailTime("1").upPriceMap(upPriceMap).downPriceMap(downPriceMap).build();
        String html = parseHtml(build);


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
//        String htmlPath = "D:\\output.html";
//        try (PrintWriter writer = new PrintWriter(new File(htmlPath))) {
//            engine.process("activity.html", context, writer);
//        }
    }

    /**
     * 将 appPriceInfoVO 中的数据渲染到 latest5ChangeTable 这个模板上
     *
     * @param appPriceInfoVO 包含信息的 AppPriceInfoVO
     * @return 数据渲染成功后的 HTML 字符串
     */
    public String parseHtml(AppPriceInfoVO appPriceInfoVO) {
        appPriceInfoVO = this.getMaxOfLimit(appPriceInfoVO, 5);
        Context context = new ContextBuilder().set("appPriceInfoVO", appPriceInfoVO).build();
        return templateEngine.process("latest5ChangeTable.html", context);
    }

    /**
     * 获取 APP 最近最多 limit 次的价格变化信息
     *
     * @param infoVO AppPriceInfoVO
     * @param limit  次数限制
     * @return 新的 AppPriceInfoVO
     */
    @SuppressWarnings("SameParameterValue")
    private AppPriceInfoVO getMaxOfLimit(AppPriceInfoVO infoVO, int limit) {
        Map<String, List<AppInfo>> upPriceMap = infoVO.getUpPriceMap();
        Map<String, List<AppInfo>> upPriceMaxOfLimit = new HashMap<>(upPriceMap.size());
        Map<String, List<AppInfo>> downPriceMap = infoVO.getDownPriceMap();
        Map<String, List<AppInfo>> downPriceMaxOfLimit = new HashMap<>(downPriceMap.size());
        upPriceMap.forEach((appName, appInfoList) -> {
            List<AppInfo> listMaxOfLimit = appInfoList.stream().limit(limit).collect(Collectors.toList());
            upPriceMaxOfLimit.put(appName, listMaxOfLimit);
        });
        downPriceMap.forEach((appName, appInfoList) -> {
            List<AppInfo> listMaxOfLimit = appInfoList.stream().limit(limit).collect(Collectors.toList());
            downPriceMaxOfLimit.put(appName, listMaxOfLimit);
        });
        int upPriceNum = this.getAppNum(upPriceMaxOfLimit);
        int downPriceNum = this.getAppNum(downPriceMaxOfLimit);
        return AppPriceInfoVO.builder().upPriceMap(upPriceMaxOfLimit).downPriceMap(downPriceMaxOfLimit)
                .upNum(upPriceNum).downNum(downPriceNum).mailTime(infoVO.getMailTime()).build();
    }


    /**
     * 提取出的公共方法，获取到 价格变化map对应的App条目数
     *
     * @param priceChangeMap 价格变化的App map
     * @return 对应的条目数
     */
    private int getAppNum(Map<String, List<AppInfo>> priceChangeMap) {
        return priceChangeMap.values().stream().map(List::size).reduce(Integer::sum).orElse(0);
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
