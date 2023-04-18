

springboot炖mybatis-plus

## 一、什么是MybatisPlus

> 为什么要学MybatisPlus？

MybatisPlus可以节省大量时间，所有的CRUD代码都可以自动化完成

MyBatis-Plus是一个MyBatis的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。

特性：

- 无侵入：只做增强不做改变，引入它不会对现有工程产生影响，如丝般顺滑

- 损耗小：启动即会自动注入基本 CURD，性能基本无损耗，直接面向对象操作

- 强大的 CRUD 操作：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，更有强大的条件构造器，满足各类使用需求

- 支持 Lambda 形式调用：通过 Lambda 表达式，方便的编写各类查询条件，无需再担心字段写错

- 支持主键自动生成：支持多达 4 种主键策略（内含分布式唯一 ID 生成器 - Sequence），可自由配置，完美解决主键问题

- 支持 ActiveRecord 模式：支持 ActiveRecord 形式调用，实体类只需继承 Model 类即可进行强大的 CRUD 操作

- 支持自定义全局通用操作：支持全局通用方法注入（ Write once, use anywhere ）

- 内置代码生成器：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 、 Service 、 Controller 层代码，支持模板引擎，更有超多自定义配置等您来使用

- 内置分页插件：基于 MyBatis 物理分页，开发者无需关心具体操作，配置好插件之后，写分页等同于普通 List 查询

- 分页插件支持多种数据库：支持 MySQL、MariaDB、Oracle、DB2、H2、HSQL、SQLite、Postgre、SQLServer 等多种数据库

- 内置性能分析插件：可输出 SQL 语句以及其执行时间，建议开发测试时启用该功能，能快速揪出慢查询

- 内置全局拦截插件：提供全表 delete 、 update 操作智能分析阻断，也可自定义拦截规则，预防误操作


## 二、快速入门

### 2.1、创建数据库mybatis_plus

### 2.2、创建user表

~~~sql
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id BIGINT(20) NOT NULL COMMENT '主键ID',
    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age INT(11) NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
);
~~~

### 2.3、插入数据

~~~sql
DELETE FROM user;

INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');
~~~



### 2.4、初始化项目

快速初始化一个空的spring boot 项目

### 2.5、添加依赖

引用spring boot starter 父工程

~~~xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.6.5</version>
    <relativePath/>
</parent>
~~~

引入spring-boot-starter、spring-boot-starter-test、mybatis-plus-boot-starter、h2依赖：

~~~xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
        <version>3.5.1</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.16</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.24</version>
    </dependency>
</dependencies>
~~~

注意：尽量不要同时导入mybatis和mybatis_plus,版本差异

### 2.6、配置(连接数据库)

在application.yml配置文件中添加MySQL数据库的相关配置：

~~~yaml
# DataSource Config
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8&tinyInt1isBit=false&useSSL=false&serverTimezone=GMT
    username: root
    password: xxxxxx
~~~

在spring boot启动类中添加@MapperScan注解，扫描Mapper文件夹：

```java
@SpringBootApplication
@MapperScan("com.xxl.mybatis_plus.mapper")  //扫描mapper
public class MybatisPlusApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApplication.class, args);
    }
}
```

### 2.7、编码

编写实体类User.java（此处使用Lombok简化代码）

~~~
import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
~~~

编写Mapper包下的UserMapper接口

~~~java
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wen.mybatis_plus.pojo.User;
import org.apache.ibatis.annotations.Mapper;

//再对应的mapper上面实现基本的接口 BaseMapper
@Mapper
public interface UserMapper extends BaseMapper<User> {
    //所有的CRUD都已经完成
    //不需要像以前一样配置一大堆文件：pojo-dao（连接mybatis，配置mapper.xml文件）==>service-controller
}
~~~

> **注意：**
>
> **要在主启动类上去扫描mapper包下的所有接口：@MapperScan("com.wen.mybatis_plus.mapper")**

### 2.8、开始使用

添加测试类，进行功能测试：

~~~java
@SpringBootTest
public class MybatisPlusApplicationTests {

    //继承了BaseMapper所有的方法，可以编写自己的扩展方法
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect(){
        System.out.println("--------selectAll method test-------");
        //查询全部用户，参数是一个Wrapper，条件构造器，先不使用为null
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }
}
~~~

> **提示：**
>
> **UserMapper中的selectList()方法的参数为MP内置的条件封装器Wrapper，所以不填写就是无任何条件**。

控制台输出：

~~~
User(id=1, name=Jone, age=18, email=test1@baomidou.com)
User(id=2, name=Jack, age=20, email=test2@baomidou.com)
User(id=3, name=Tom, age=28, email=test3@baomidou.com)
User(id=4, name=Sandy, age=21, email=test4@baomidou.com)
User(id=5, name=Billie, age=24, email=test5@baomidou.com)
~~~

### 2.9、小结

以上几个步骤就已经实现User表的CRUD功能，甚至连XML文件都不用编写，以上步骤可以看出集成MyBatis-Plus非常的简单，只需要引入starter工程，并配置mapper扫描路径即可。方法都是MyBatis-Plus写好的，直接引用即可。

## 三、配置日志

> 所有的SQL都是不可见的，所以在后台是希望看到SQL是怎么执行的，就必须要配置日志。

在.yml配置文件中配置日志：

```yaml
#配置日志
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

打印日志

~~~
--------selectAll method test-------
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1693ff90] was not registered for synchronization because synchronization is not active
JDBC Connection [HikariProxyConnection@221692355 wrapping com.mysql.cj.jdbc.ConnectionImpl@96abc76] will not be managed by Spring
==>  Preparing: SELECT id,`name`,age,email FROM user 
==> Parameters: 
<==    Columns: id, name, age, email
<==        Row: 1, Jone, 18, test1@baomidou.com
<==        Row: 2, Jack, 20, test2@baomidou.com
<==        Row: 3, Tom, 28, test3@baomidou.com
<==        Row: 4, Sandy, 21, test4@baomidou.com
<==        Row: 5, Billie, 24, test5@baomidou.com
<==      Total: 5
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1693ff90]
User(id=1, name=Jone, age=18, email=test1@baomidou.com)
User(id=2, name=Jack, age=20, email=test2@baomidou.com)
User(id=3, name=Tom, age=28, email=test3@baomidou.com)
User(id=4, name=Sandy, age=21, email=test4@baomidou.com)
User(id=5, name=Billie, age=24, email=test5@baomidou.com)
~~~

## 四、CRUD

### 4.1、插入测试

~~~
//测试插入
@Test
public void testInsert(){
    User user = new User();
    user.setName("小文");
    user.setAge(21);
    user.setEmail("2312103645@qq.com");

    int insert = userMapper.insert(user);//如果没有设置id，那么会自动生成id
    System.out.println(insert);//受影响行数
    System.out.println(user);//id会自动回填
}
~~~





## 八、执行SQL分析打印

可输出 SQL 语句以及其执⾏时间，建议开发测试时启⽤该功能，能快速揪出慢查询

注意：PerformanceInterceptor在3.2.0被移除了，如果想进⾏性能分析，⽤第三⽅的，官⽅这样写的“该插件 3.2.0 以上版本移除 推荐使⽤第三⽅扩展 执⾏SQL分析打印 功能”。也就是p6spy。

使用步骤：

#### 8.1、p6spy依赖引入

Maven:

~~~xml
<dependency>
  <groupId>p6spy</groupId>
  <artifactId>p6spy</artifactId>
  <version>最新版本</version> <!--这里用的是>3.9.1版本-->
</dependency>
~~~

### 8.2、application.yml配置

~~~
spring:
  datasource:
    driver-class-name: com.p6spy.engine.spy.P6SpyDriver
    url: jdbc:p6spy:h2:mem:test
    ...
~~~

> 注意： driver-class-name 为 p6spy 提供的驱动类，url 前缀为 jdbc:p6spy 跟着冒号为对应数据库连接地址

实际配置为：

~~~xml
spring:
  datasource:
    driver-class-name: com.p6spy.engine.spy.P6SpyDriver
    url: jdbc:p6spy:mysql:///mybatis_plus?userUnicode=true&characterEncoding=utf-8
    username: root
    password: 123456
    
~~~









## 参考资料

https://blog.csdn.net/m0_46313726/article/details/124187527