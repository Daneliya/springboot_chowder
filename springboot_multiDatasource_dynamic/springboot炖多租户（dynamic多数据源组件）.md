
~~~
数据源配置在yml中
@DS 绑定数据源
@DSTransactional 控制事务

@Transactional 和 @DSTransactional 混用可能会造成事务死锁
~~~

dynamic **学习**：
https://blog.csdn.net/qq877728715/article/details/115374991
SpringBoot实现多数据源（六）【dynamic-datasource 多数据源组件】：https://blog.csdn.net/Wei_Naijia/article/details/128069866
图灵视频：https://www.bilibili.com/video/BV1er4y1e7h2
图灵视频文档：https://blog.csdn.net/guoguo0717/article/details/124795657



dynamic-datasource官方文档：
https://baomidou.com/pages/a61e1b/#dynamic-datasource
https://www.kancloud.cn/tracy5546/dynamic-datasource/2264611

dynamic 案例方案：
https://blog.csdn.net/m0_37635053/article/details/126735438 (主要参考)
https://blog.csdn.net/sinat_25519789/article/details/124265311
https://blog.csdn.net/dzd9527/article/details/125534489
https://blog.csdn.net/LBWNB_Java/article/details/126172964
https://blog.csdn.net/qq_17215887/article/details/124167029
https://zhizhi.pcwanli.com/front/article/24326.html
https://blog.csdn.net/wdj_yyds/article/details/122460411
https://blog.csdn.net/weixin_45032957/article/details/108711493
https://blog.51cto.com/u_2837193/4956506
https://www.jb51.net/article/233977.htm

dynamic 原理：
https://blog.csdn.net/weixin_42144707/article/details/112109491

spring bean加载顺序：https://blog.csdn.net/weixin_33166692/article/details/112588201





### 问题：

#### @Component将普通类实例化spring容器中，但注入service等失败

方案一：把@Autowired注解放在方法上
https://blog.csdn.net/Ber_Bai/article/details/128397794
方案二：@PostConstruct
https://blog.51cto.com/gblfy/5653581
https://www.cnblogs.com/shizhe99/p/15579881.html
https://blog.csdn.net/qq_43470725/article/details/117674732
方案三：getBean
https://blog.csdn.net/qrnhhhh/article/details/88977313
https://blog.csdn.net/gg4236131/article/details/114653322










### 面试题

~~~markdown
为什么使用？
多数据源既动态数据源，项目开发逐渐扩大，单个数据源、单一数据源已经无法满足需求项目的支撑需求。
dynamic 本框架只做 切换数据源 这件核心的事情，并不限制你的具体操作，切换了数据源可以做任何CRUD。

项目中怎么做？
项目中使用拦截器实现，多数据源配置(即不同租户进来使用对应的数据源)，动态添加或删除数据源。
扩展 Spring 的 AbstractRoutingDataSource 抽象类继承类。
总结 大致思想就是，mybatis执行SQL时会从DataSource拿一个JDBC连接，所以spring巧妙的利用了这个特点，它提前拿到多个数据源的实例信息，在mybatis获取连接时根据用户的指令，动态的选择返回哪个连接，这些操作对mybatis是透明的。 spring把一些通用方法高度抽象到 AbstractRoutingDataSource 抽象类中，使用两个变量管理数据源， targetDataSources 和 defaultTargetDataSource ，然后再预留出 determineCurrentLookupKey 方法供我们实现，即只需要返回一个key就可以，返回的key用于从 targetDataSources 中选取出我们指定的数据源。 所以，除了 determineCurrentLookupKey 方法外，其他的操作对于使用者来说也是透明的，使用者只需要关心如何在 determineCurrentLookupKey 方法中实现自己的选择数据源的规则即可。 只不过，我们是通过AOP切面拦截的方式增强方法，在持久层方法执行前即在mybatis执行SQL前我们事先把 DataSource 换成我们指定的即可。 在这里我们换数据源的方式是通过一个 DataSourceHolder 类中的 ThreadLocal 实现的，原因是为了保证多线程并发环境下不同线程切换数据源时不会乱， Threadlocal 线程独有的一个对象，在其内部保存我们的key，在 determineCurrentLookupKey 中获取并返回即可。
~~~









