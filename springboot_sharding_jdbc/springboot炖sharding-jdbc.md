













### 配置

#### sharding3

分库分表（sharding3版本）：https://blog.csdn.net/zxp2624161989/article/details/107094560/

不分库只分表（sharding3版本）：https://www.bilibili.com/video/BV1id4y1z7St、https://blog.csdn.net/zxp2624161989/article/details/107094560/

水平分片：
创建测试数据局test_order。分别创建三张表t_address， t_user0，t_user1。
这里假设t_user这个预计随着系统的运行。
公司发展很好，以后数据量会暴增。所以提前进行水平分片存储。相对于垂直分片，它不再将数据根据业务逻辑分类，
而是通过某个字段（或某几个字段），根据某种规则将数据分散至多个库或表中，
每个分片仅包含数据的一部分。这样单表数据量降下来了，mysql的B+树的检索效率就提高了。

#### sharding4

不分库只分表（sharding4版本）yml配置：https://blog.csdn.net/weixin_40816738/article/details/126802777
分表配置后加载报错，ShardingParsingRuleRegistry：https://www.codenong.com/cs105364582/
方法一：将 jdk的运行版本降为 1.8。
方法二：将 JAXB 相关jar包重新引入，具体maven。

分库分表（sharding4版本）yml配置：https://blog.csdn.net/akenseren/article/details/127350807
https://www.jianshu.com/p/3b3f7c6fd288










### 参考资料

[1]. 狂神视频：https://www.bilibili.com/video/BV1ei4y1K7dn
狂神笔记：https://blog.csdn.net/qq_44866424/article/details/120009099

[2]. win下配置主从复制（不推荐在win上配置）
https://blog.csdn.net/qq_27991253/article/details/128017412



### 面试问题

1、与mycat区别：

https://www.zhihu.com/question/64709787







### sharding使用中的一些问题：

1、sharding + MP 启动后无法执行查询：Error querying database？

解决方式：修改版本为4.1.1，使其支持子查询。

https://blog.csdn.net/u014106644/article/details/128335532

4.0.0-RC1版本是有限制支持子查询的 主查询和子查询必须保证相同的分片键;

4.1.1可以支持子查询  子查询判断条件恒为false;

5.0版本 Federation 执行引擎支持子查询；

2、警告：spring boot 集成 sharding jdbc 分库分表 数据库连接健康检查不通过

https://www.cnblogs.com/maohuidong/p/15006724.html

3、服务器配置主从复制报错：Fatal error:The slave I/O thread stops because master and slave have equal MySQL server UUIDs？

原因：使用了VMware克隆了两台虚拟机作为主机和从机导致UUID一样。

解决：找到主机和从机的auto.cnf文件修改uuid值或删除auto.cnf这个文件。

https://blog.csdn.net/cnds123321/article/details/117925881

4、Sharding-JDBC整合Mybatisplus分片键生成策略冲突问题及分析解决

https://blog.csdn.net/weixin_43584430/article/details/120367418

