package com.xxl.mongodb;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.xxl.mongodb.result.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author xxl
 * @date 2024/1/2 0:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AggregationTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Aggregation查询
     */
    @Test
    public void query001() {
        int page = 1;
        int size = 10;
        Date startTime = DateUtil.parse("1900-01-01 00:00:00");
        Date endTime = new Date();
        String userName = "xxl";

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("birthday").gte(startTime).lte(endTime)),
                Aggregation.match(Criteria.where("userName").is(userName)),
                Aggregation.group("idNumber").count().as("sum")
                        .first("userName").as("userName")
                        .first("phoneNumber").as("phoneNumber")
                        .first("birthday").as("birthday"),
                Aggregation.sort(Sort.by("sum").descending()),
                Aggregation.skip(page > 1 ? (page - 1) * size : 0),
                Aggregation.limit(size)
        );

        List<SysUser> results = mongoTemplate.aggregate(aggregation, "sys_user", SysUser.class).getMappedResults();
        System.out.println(results);
    }

    @Test
    public void projectTest() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group(new String[]{"_id"})
                        .sum("money").as("sum")
                        .first("userName").as("userName")
                        .first("phoneNumber").as("phoneNumber")
                        .last("birthday").as("birthday"),
                Aggregation.project("_id", "sum", "userName", "phoneNumber")
                        .and("birthday").as("birth") // 重新命名字段
        );
        List<SysUser> results = mongoTemplate.aggregate(aggregation, "sys_user", SysUser.class).getMappedResults();
        System.out.println(results);
    }

    @Test
    public void unwindTest() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(new Criteria().and("userName").is("xxl")),
                Aggregation.unwind("tags", true)
        );
        List<SysUser> results = mongoTemplate.aggregate(aggregation, "sys_user", SysUser.class).getMappedResults();
        System.out.println(results);
    }


    @Test
    public void lookupTest() {
        Aggregation aggregation = Aggregation.newAggregation(
                //分别对应from, localField, foreignField, as
                Aggregation.lookup("sys_user_label", "userName", "user_name", "label")
        );
        List<SysUser> results = mongoTemplate.aggregate(aggregation, "sys_user", SysUser.class).getMappedResults();
        System.out.println(results);
    }
}
