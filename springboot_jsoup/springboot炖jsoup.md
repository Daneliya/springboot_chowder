## springboot炖jsoup

### 1. 先睹为快

简述

```markdown
Java中支持的爬虫框架有很多，比如WebMagic、Spider、Jsoup等。本文主要介绍Jsoup，并实现简单的爬虫程序。

Jsoup拥有十分方便的api来处理html文档，比如参考了DOM对象的文档遍历方法，参考了CSS选择器的用法等等，因此我们可以使用Jsoup快速地掌握爬取页面数据的技巧。

```

### 2. 实现原理


#### 2.1 新建项目


#### 2.2 创建maven目录结构，以及pom.xml文件



#### 2.3 pom.xml文件中加入依赖

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.0.5.RELEASE</version>
    <relativePath/>
</parent>
```

#### 2.4 pom.xml文件中加入springboot-starter依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.jsoup</groupId>
        <artifactId>jsoup</artifactId>
        <version>1.10.3</version>
    </dependency>
</dependencies>
```

#### 2.5 pom.xml文件中加入maven-springboot打包插件

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

#### 2.6 开发启动类

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

#### 2.7 开发工具类

```java
public class HtmlParseUtil {
    public static void main(String[] args) {
        new HtmlParseUtil().parseJD("hello").forEach(System.out::println);
    }

    public List<Content> parseJD(String keywords) throws IOException {
        // 获取请求  https://search.jd.com/Search?keyword=java
        // 前提，需要联网！  ajax需要模拟浏览器才能获取到！ 以京东搜索为例

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
```


#### 2.8 开发用户实体类

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Content {
    private String title;
    private String img;
    private String price;
    // 可以自己添加属性！
}
```


### 3. 应用场景


### 参考资料

[1].【狂神说Java】Jsoup爬虫入门实战：https://www.bilibili.com/video/BV1La4y1x7Wm
