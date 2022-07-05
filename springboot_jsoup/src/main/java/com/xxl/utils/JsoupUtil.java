package com.xxl.utils;

import com.xxl.entity.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xxl
 * @Date: 2022/07/05 11:35
 */
public class JsoupUtil {
    public static void main(String[] args) throws IOException {
        new JsoupUtil().parseJD("hello").forEach(System.out::println);
    }

    public List<Content> parseJD(String keywords) throws IOException {
        // 获取请求  https://search.jd.com/Search?keyword=java
        // 前提，需要联网！  ajax需要模拟浏览器才能获取到！

        String url = "https://search.jd.com/Search?keyword=" + keywords;
        // 解析网页。（Jsoup返回Document就是浏览器Document对象）
        Document document = Jsoup.parse(new URL(url), 30000);
        // 所有你在js可以使用的方法，这里都能用！
        Element element = document.getElementById("J_goodsList");
        // 获取所有的li元素
        Elements elements = element.getElementsByTag("li");

        ArrayList<Content> goodsList = new ArrayList<>();

        // 获取元素中的内容，这里 el 就是每一个 li 标签了！
        for (Element el : elements) {
            // 关于这种图片特别多的网站，所有的图片都是延迟加载的！
            // src 拿不到图片，通过 element.html() 打印可以看到在 source-data-lazy-img（2021-01-15测试发现在 data-lazy-img） 下
            String img = el.getElementsByTag("img").eq(0).attr("data-lazy-img");//img标签，第一个元素，src属性
            String price = el.getElementsByClass("p-price").eq(0).text();
            String title = el.getElementsByClass("p-name").eq(0).text();

            Content content = new Content();

            content.setTitle(title);
            content.setPrice(price);
            content.setImg(img);

            goodsList.add(content);
        }
        return goodsList;
    }
}
