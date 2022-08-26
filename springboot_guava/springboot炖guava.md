## springboot炖guava



### 前言

Guava是一种基于开源的Java库，Google Guava源于2007年的"Google Collections Library"。这个库是为了方便编码，并减少编码错误。这个库用于提供集合，缓存，支持原语句，并发性，常见注解，字符串处理，I/O和验证的实用方法。

### 一、Guava 的好处 ：

- 标准化 - Guava库是由谷歌托管。
- 高效 - 可靠，快速和有效的扩展JAVA标准库
- 优化 -Guava库经过高度的优化。
- 函数式编程：增加Java功能和处理能力

实用程序：提供经常需要在应用程序中开发的许多实用程序类

验证：提供标准的故障安全验证机制。

最佳实践：强调最佳的做法。

它是一个提高代码质量、简化工作，促使代码更有弹性、更加简洁的工具。



### 二、Guava 高质量的 API：

#### 1、项目相关信息

[官方首页](https://github.com/google/guava)

#### 2、源码包的简单说明

~~~markdown
com.google.common.annotations：普通注解类型。
com.google.common.base：基本工具类库和接口。
com.google.common.cache：缓存工具包，非常简单易用且功能强大的JVM内缓存。
com.google.common.collect：带泛型的集合接口扩展和实现，以及工具类，这里你会发现很多好玩的集合。
com.google.common.eventbus：发布订阅风格的事件总线。
com.google.common.hash： 哈希工具包。
com.google.common.io：I/O工具包。
com.google.common.math：原始算术类型和超大数的运算工具包。
com.google.common.net：网络工具包。
com.google.common.primitives：八种原始类型和无符号类型的静态工具包。
com.google.common.reflect：反射工具包。
com.google.common.util.concurrent：多线程工具包。
~~~

#### 3、类库使用手册

（1）基本工具类：让使用Java语言更令人愉悦。

1、使用和避免 null：null 有语言歧义， 会产生令人费解的错误， 反正他总是让人不爽。很多 Guava 的工具类在遇到 null 时会直接拒绝或出错，而不是默默地接受他们。

2、前提条件：更容易的对你的方法进行前提条件的测试。

3、常见的对象方法： 简化了Object常用方法的实现， 如 hashCode() 和 toString()。

4、排序： Guava 强大的 "fluent Comparator"比较器， 提供多关键字排序。

5、Throwable类： 简化了异常检查和错误传播。

（2）集合类：集合类库是 Guava 对 JDK 集合类的扩展。

1、Immutable collections（不变的集合）： 防御性编程， 不可修改的集合，并且提高了效率。

2、New collection types(新集合类型)：JDK collections 没有的一些集合类型，主要有：multisets，multimaps，tables， bidirectional maps等等
3、Powerful collection utilities（强大的集合工具类）： java.util.Collections 中未包含的常用操作工具类
4、Extension utilities（扩展工具类）: 给 Collection 对象添加一个装饰器? 实现迭代器? 我们可以更容易使用这些方法。

（3） 缓存: 本地缓存，可以很方便的操作缓存对象，并且支持各种缓存失效行为模式。

（4）Functional idioms（函数式）: 简洁, Guava实现了Java的函数式编程，可以显著简化代码。

（5）Concurrency（并发）：强大,简单的抽象,让我们更容易实现简单正确的并发性代码。

1、ListenableFuture（可监听的Future）: Futures,用于异步完成的回调。
2、Service: 控制事件的启动和关闭，为你管理复杂的状态逻辑。

（6）Strings: 一个非常非常有用的字符串工具类: 提供 splitting，joining， padding 等操作。

（7）Primitives: 扩展 JDK 中未提供的对原生类型（如int、char等）的操作， 包括某些类型的无符号的变量。

（8）Ranges: Guava 一个强大的 API，提供 Comparable 类型的范围处理， 包括连续和离散的情况。

（9）I/O: 简化 I/O 操作, 特别是对 I/O 流和文件的操作, for Java 5 and 6.

（10）Hashing: 提供比 Object.hashCode() 更复杂的 hash 方法, 提供 Bloom filters.

（11）EventBus: 基于发布-订阅模式的组件通信，但是不需要明确地注册在委托对象中。

（12）Math: 优化的 math 工具类，经过完整测试。

（13）Reflection: Guava 的 Java 反射机制工具类。





### 参考资料：

[1]. [Guava简介(详细)](https://blog.csdn.net/weixin_42326851/article/details/124364719)

[2]. [Guava - 拯救垃圾代码，写出优雅高效，效率提升N倍](https://blog.csdn.net/qing_gee/article/details/113393534)