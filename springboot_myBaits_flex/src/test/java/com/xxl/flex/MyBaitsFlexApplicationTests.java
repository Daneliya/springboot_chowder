package com.xxl.flex;

import com.mybatisflex.core.MybatisFlexBootstrap;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.xxl.flex.entity.Account;
import com.xxl.flex.mapper.AccountMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MyBaitsFlexApplicationTests {

    @Test
    public void noSpringTest() {
        //创建数据源
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/mybatis_plus?characterEncoding=utf-8&serverTimezone=UTC&useSSL=true");
        dataSource.setUsername("root");
        dataSource.setPassword("xxl666");

        //配置数据源
        MybatisFlexBootstrap.getInstance()
                .setDataSource(dataSource)
                .addMapper(AccountMapper.class)
                .start();

        //获取 mapper
        AccountMapper mapper = MybatisFlexBootstrap.getInstance()
                .getMapper(AccountMapper.class);

        //示例1：查询 id=1 的数据
        Account account = mapper.selectOneById(1);
        System.out.println(account);

        //示例2：者使用 Db + Row 查询
        String sql = "select * from account where age > ?";
        List<Row> rows = Db.selectListBySql(sql, 18);
        System.out.println(rows);
    }

}
