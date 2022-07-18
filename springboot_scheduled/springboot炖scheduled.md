## springboot炖scheduled

### 1. 先睹为快

内容
- Scheduled 定时任务器（简单，不复杂）
- 整合 Quartz 定时任务框架（定时任务调度复杂）


### 2. 实现 Scheduled 定时任务器

Scheduled 定时任务器：是 Spring 3.0 以后自带的一个定时任务器。

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

    <!--添加Scheduled坐标-->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context-support</artifactId>
        <version>5.0.5.RELEASE</version>
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
@SpringBootApplication
public class ScheduledApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduledApplication.class, args);
    }

}
```

#### 2.7 编写定时任务类

```java
@Component
public class ScheduledDemo {

    /**
     * 定时任务方法
     *  @Scheduled: 设置定时任务。
     *  cron 属性：cron表达式。定时任务触发时间的一个字符串表达形式。
     */
    @Scheduled(cron = "0/2 * * * * ?")
    public void ScheduledMethod(){
        System.out.println("定时器被触发：" + new Date());
    }
}
```


#### 2.8 cron 表达式讲解

Cron 表达式是一个字符串，分为6或7个域，每一个域代表一个含义。

Cron 有如下两种语法：

（1）Seconds Minutes Hours Day month Week Year
（2）Seconds Minutes Hours Day Month Week

<font color="#dd0000">一、结构</font>

corn从左到右（用空格隔开）：秒  分  小时  月份中的日期  月份  星期中的日期  年份

<font color="#dd0000">二、备字段的含义</font>

| 位置 | 时间域名 | 允许值    | 允许的特殊字符         |
| ---- | -------- | --------- | ---------------------- |
| 1    | 秒       | 0-59      | ，-  *  /              |
| 2    | 分钟     | 0-59      | ,   -  *  /            |
| 3    | 小时     | 0-23      | ,  -  *  /             |
| 4    | 日       | 1-31      | ,  -  *  /  L  W  C    |
| 5    | 月       | 1-12      | ,  -  *  /             |
| 6    | 星期     | 1-7       | ,  -  *  ?  /  L  C  # |
| 7    | 年       | 1970-2099 | ,  -  *  /             |

注意：4 与 6 一般只设置一个，另一个用‘’？‘’ 站位，因为星期几与日这两个容易冲突。

Cron 表达式的时间字段除允许设置数值外，还可使用一些特殊的字符，提供列表、范围、通配符等功能，细说如下：

- 星号（\*）：可用在所有字段中，表示对应时的每一个时刻，如，在分钟字段时，表示"每分钟"；
- 问号（?）：该字符只在日期和星期字段中使用，它通常指定为"无意义的值"相当于占位符；
- 减号（-）：表达一个范围，如在小时字段中使用"10-12"，贝表示从10到12点，即10,11,12；
- 逗号（,）：表达一个列表值，如在星期字段中使用"MON,WED,FRI"，则表示星期一，星期三和星期五；
- 斜杠（/）：x/y表达一个等步长序列，x为起始值，y为增量步长值。如在分钟字段中使用o/15，则表示为0,15,30和45秒，而5/15在分钟字段中表示5,20,35,50，你也可以使用*/y，它等同于0/y;
- L：该字符只在日期和星期字段中使用，代表"Last"的意思，但它在两个字段中意思不同。L在日期字段中，表示这个月份的最后一天，如一月的31号，非闰年二月的28号；如果L用在星期中，则表示星期六，等同于7。但是，如果L出现在星期字段里，而且在前面有一个数值X，则"表示这个月的最后X天"，例如，6L表示该月的最后星期五；
- w：该字符只能岀现在日期字段里，是对前导日期修饰，表示离该日期最近的工作日。例如15W表示离该月15号最近的工作日，如果该月15号是星期六，则匹配14号星期五；如果15日是星期日，则匹配16号星期一；如果15号是星期二，那结果就是15号星期二。但必须注主意关联的匹配曰期不能够跨月，如你指定1W，如果1号是星期六，结果匹配的是3号星期一，而非上个月最后的那天。W字符串只能指定单一日期，而不能指定日期范围；
- LW组合：在日期字段可以组合使用LW，它的意思是当月的最后一个工作日
- 井号（#）：该字符只能在星期字段中使用，表示当月某个工作日。如6#3表示当月的第三个星期五（6表示星期五，#3表示当前的第三个），而4#5表示当月的第五个星期三，假设当月没有第五个星期三，忽略不触发；
- C：该字符只在日期和星期字段中使用，代表"Calendar"的意思。它的意思是计划所关联的日期，如果日期没有被关联，则相当于日历中所有日期。例如5C在日期字段中就相当于日历5日以后的第一天。1C在星期字段中相当于星期日后的第一天。



Cron表达式对特殊字符的大小写不敏感，对代表星期缩与英文大小写也不敏感。

例子：

@ Scheduled（cron="0 0 1 1 1 ?”）//每年一月的一号的1:00:00执行一次
@ Scheduled（cron="0 0 1 1 1,6 ?”）//一月和六月的一号的1:00:00执行一次
@ Scheduled（cron="0 0 1 1 1,4,7,10 ?"）//每个季度的第一个月的一号的1:00:00执行一次
@ Scheduled（cron="0 0 1 1 * ?”）//每月一号1:00:00执行一次
@ Scheduled（cron="0 0 1 * * *"）//每天凌晨1点执行一次


### 3. Spring Boot整合 Quartz定时任务框架


### 参考资料

[1].
