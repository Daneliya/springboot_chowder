

分库分表（sharding3版本）：https://blog.csdn.net/zxp2624161989/article/details/107094560/

不分库只分表（sharding3版本）：https://www.bilibili.com/video/BV1id4y1z7St、https://blog.csdn.net/zxp2624161989/article/details/107094560/

水平分片：
创建测试数据局test_order。分别创建三张表t_address， t_user0，t_user1。
这里假设t_user这个预计随着系统的运行。
公司发展很好，以后数据量会暴增。所以提前进行水平分片存储。相对于垂直分片，它不再将数据根据业务逻辑分类，
而是通过某个字段（或某几个字段），根据某种规则将数据分散至多个库或表中，
每个分片仅包含数据的一部分。这样单表数据量降下来了，mysql的B+树的检索效率就提高了。

不分库只分表（sharding4版本）yml配置：https://blog.csdn.net/weixin_40816738/article/details/126802777
分表配置后加载报错，ShardingParsingRuleRegistry：https://www.codenong.com/cs105364582/
方法一：将 jdk的运行版本降为 1.8。
方法二：将 JAXB 相关jar包重新引入，具体maven。

分库分表（sharding4版本）yml配置：https://blog.csdn.net/akenseren/article/details/127350807




狂神：https://www.bilibili.com/video/BV1ei4y1K7dn
笔记：https://blog.csdn.net/qq_44866424/article/details/120009099
配置主从复制报错Fatal error:The slave I/O thread stops because master and slave have equal MySQL server UUIDs；\
https://blog.csdn.net/cnds123321/article/details/117925881
win下配置主从复制
https://blog.csdn.net/qq_27991253/article/details/128017412