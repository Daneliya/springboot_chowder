//package com.xxl.thymeleaf.util;
//
//import java.io.File;
//
///**
// * @Author: xxl
// * @Date: 2024/11/27 15:58
// */
//public class PdfTest {
//    // html片段
//    private static final String content = "<p style=\"text-align: justify;\"><img class=\"wscnph\" src=\"http://117.78.37.58:8989/files/20200327\\png\\dcb09254b0d049d28550a9f31d8e88af.png\" /></p>\n" +
//            "<p style=\"text-align: left;\">犀牛是国bai家稀有动物之一，也是du国家级保护动物。</p>\n" +
//            "<p style=\"text-align: left;\">&nbsp;</p>\n" +
//            "<p style=\"text-align: left;\">犀牛身体庞大bai，四肢粗du壮，体重一般都在三千斤左右。它的皮又厚又硬，足以挡住任何动物的袭击。犀牛鼻子上张着一只或两只坚硬的角，在动物王国里抵抗力和杀伤力都是数一数二的。任何猛兽连人都难以打倒它，它发起怒来连附近的树木植物都难逃厄运，就连狮子、老虎等大型陆地动物在犀牛发怒时都得逃之夭夭。</p>\n" +
//            "<p style=\"text-align: left;\">&nbsp;</p>\n" +
//            "<p style=\"text-align: left;\">犀牛的皮肤虽然厚糙，可皮肤中的细缝却柔嫩，成为了寄生虫、蚊子等吸血昆虫的青睐。可它有一位如影随形的好朋友——犀牛鸟，它以犀牛皮肤空隙里的吸血虫为食。这样既帮助犀牛除出祸害，又让自己饱餐一顿，可真是一举两得啊！</p>\n" +
//            "<p style=\"text-align: left;\">&nbsp;</p>\n" +
//            "<p style=\"text-align: left;\">犀牛拥有高度近视，它的好朋友犀牛鸟却视力良好。在发现情敌的时候，犀牛鸟就会“叽叽喳喳”向犀牛提醒。这时，犀牛就会迅速逃离现场，让敌人枉费心机。</p>\n" +
//            "<p style=\"text-align: left;\">&nbsp;</p>\n" +
//            "<p style=\"text-align: left;\">虽然犀牛以稀有而收到世人的保护，可仍有一些不法之徒向犀牛伸出魔爪，让我们一起来保护犀牛，保护野生动物吧！！！</p>\n" +
//            "<p style=\"text-align: left;\">&nbsp;</p>";
//
//    public static void main(String[] args) {
//
//        // 把html片段中的图片url替换成相对url,采用Jsoup解析
//        String replaceImgTagSrc = PdfUtils.replaceImgTagSrc(content);
//        //把替换后的html片段根据Thymeleaf模板生成html
//        String html = PdfUtils.getHtml(PdfUtils.HTML_TEMPLATE_PATH, replaceImgTagSrc);
//        // 把html转成pdf，这里将生成的pdf文件放在E盘下
//        PdfUtils.htmlToPdf(html, new File("D:/pdfTest.pdf"));
//    }
//}
