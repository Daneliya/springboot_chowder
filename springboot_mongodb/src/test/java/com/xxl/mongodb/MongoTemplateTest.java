package com.xxl.mongodb;

import com.github.javafaker.Faker;
import com.xxl.mongodb.result.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xxl
 * @date 2023/12/21 0:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoTemplateTest {

    /**
     * faker 指定汉语，默认英语
     */
    private static Faker FAKER = new Faker(Locale.CHINA);

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 初始化数据
     */
    @Test
    public void init() {
        // 清空数据表
        Query query = new Query();
        mongoTemplate.remove(query, SysUser.class);
        // 构造测试数据
        List<SysUser> sysUserList = Stream.generate(() -> new SysUser(
                        FAKER.name().fullName(),
                        FAKER.phoneNumber().cellPhone(),
                        FAKER.address().city() + FAKER.address().streetAddress(),
                        FAKER.idNumber().validSvSeSsn(),
                        FAKER.date().birthday()))
                .limit(10000)
                .collect(Collectors.toList());
        mongoTemplate.insert(sysUserList, SysUser.class);
    }

    @Test
    public void query001() {
        // 查询 userName=xxl，结果为集合列表
//        Query query = Query.query(Criteria.where("userName").is("xxl"));
//        mongoTemplate.find(query, SysUser.class);
//        mongoTemplate.find(query, SysUser.class, "sys_user");
//        // 查询所有，结果为集合列表
//        mongoTemplate.findAll(SysUser.class);
//        mongoTemplate.findAll(SysUser.class, "sys_user");

        // 分页查询（page页码，pageSize每页展示几个）
        int page = 1;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Order.desc("birthday")));
        Query query = new Query().with(pageable);
        List<SysUser> sysUserList = mongoTemplate.find(query, SysUser.class);
        System.out.println(sysUserList);
        mongoTemplate.find(query, SysUser.class, "sys_user");
//
//        // 查询多个
//        Query query = Query.query(Criteria.where("id").in("id1", "id2", "id3")).with(Sort.by(Sort.Order.desc("birthday")));
//        mongoTemplate.find(query, SysUser.class);
//
//        // 查询数量
//        Criteria criteria = Criteria.where("userId").is("12345")
//                .and("name").is(new ObjectId("张三"))
//                .and("address").is("上海");
//        Query query = Query.query(criteria);
//        long count = mongoTemplate.count(query, SysUser.class);
    }


}
