# springboot炖listener

### 1. 先睹为快

### 2. 实现原理

#### 2.1 新建项目



#### 2.2 创建maven目录结构，以及pom.xml文件



#### 2.3 pom.xml文件中加入依赖

```
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.0.5.RELEASE</version>
    <relativePath/>
</parent>
```

#### 2.4 pom.xml文件中加入springboot-starter依赖

```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

#### 2.5 pom.xml文件中加入maven-springboot打包插件

```
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

```
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

#### 2.7 开发测试接口

```
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @RequestMapping("/test")
    public Object test(String name) {
        return "hello " + name;
    }

}
```

#### 2.8 开发监听器

```
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletRequestListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        System.out.println("监听器销毁。。。");
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        System.out.println("监听器初始化。。。");
        String name = servletRequestEvent.getServletRequest().getParameter("name");
        System.out.println(name);
    }

}
```

#### 2.9 编译打包运行

http://localhost:port/test?name=xxl

### 3. 应用场景

参考资料

[1]. Spring源码分析——AnnotationConfigApplicationContext组件注册流程 https://www.cnblogs.com/baiyuxuan/p/14953165.html
[2]. ApplicationRunner和CommandLineRunner https://blog.csdn.net/yhahaha_/article/details/90696921
[3]. ListenerJavaWeb核心技术之Listener监听器 https://blog.csdn.net/helpluozhao123/article/details/122628373
https://blog.csdn.net/PpACf/article/details/124749344
https://blog.csdn.net/Leon_Jinhai_Sun/article/details/108785191