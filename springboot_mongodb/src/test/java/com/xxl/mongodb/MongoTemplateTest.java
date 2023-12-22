package com.xxl.mongodb;

import com.github.javafaker.Faker;
import com.xxl.mongodb.result.SysUser;
import org.bson.types.ObjectId;
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
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
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

    /**
     * 查询
     */
    @Test
    public void query001() {
        // 查询 userName=xxl，结果为集合列表
//        Query query = Query.query(Criteria.where("userName").is("xxl"));
//        mongoTemplate.find(query, SysUser.class);
//        mongoTemplate.find(query, SysUser.class, "sys_user");
//        // 查询所有，结果为集合列表
//        mongoTemplate.findAll(SysUser.class);
//        mongoTemplate.findAll(SysUser.class, "sys_user");
//
//        // 分页查询（page页码，pageSize每页展示几个）
//        int page = 1;
//        int pageSize = 10;
//        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Order.desc("birthday")));
//        Query query2 = new Query().with(pageable);
//        mongoTemplate.find(query2, SysUser.class);
//        mongoTemplate.find(query2, SysUser.class, "sys_user");
//
//        // 查询多个
        Query query3 = Query.query(Criteria.where("id").in("id1", "id2", "id3")).with(Sort.by(Sort.Order.desc("birthday")));
        mongoTemplate.find(query3, SysUser.class);
        System.out.println("SQL：" + query3.toString());

        // 查询数量
        Criteria criteria = Criteria.where("_id").is("658596256a3c743ad03b432a")
                .and("_id").is(new ObjectId("658596256a3c743ad03b432a"))
                .and("address").is("地址");
        Query query4 = Query.query(criteria);
        long count = mongoTemplate.count(query4, SysUser.class);
        System.out.println(count);
        System.out.println("SQL：" + query4.toString());
    }

    @Test
    public void query002() {
        List<SysUser> list = new ArrayList<>();
        SysUser user = new SysUser();
        user.setUserName("admin");
        user.setAddress("地址");
        list.add(user);

        // 保存对象到mongodb
        mongoTemplate.save(user);
        mongoTemplate.insert(user);
        // 根据集合名称保存对象到mongodb
        mongoTemplate.save(user, "sys_user");
        mongoTemplate.insert(user, "sys_user");
        // 根据集合名称保存list到mongodb
        mongoTemplate.save(list, "sys_user");
        mongoTemplate.insert(list, "sys_user");
        mongoTemplate.insert(list, SysUser.class);
    }
    @Test
    public void query003() {
        SysUser user = new SysUser();
        user.setId("5d1312aeb1829c279c6c256b");
        user.setName("admin");
        user.setAddress("测试");

        Query query = Query.query(Criteria.where("_id").is("5d1312aeb1829c279c6c256b"));
        Update update = Update.update("name","zs");
//  更新一条数据
        mongoTemplate.updateFirst(query,update, User.class);
        mongoTemplate.updateFirst(query,update, "mongodb_user");
        mongoTemplate.updateFirst(query,update, User.class,"mongodb_user");
//  更新多条数据
        mongoTemplate.updateMulti(query,update, User.class);
        mongoTemplate.updateMulti(query,update,"mongodb_user");
        mongoTemplate.updateMulti(query,update, User.class,"mongodb_user");
//  更新数据，如果数据不存在就新增
        mongoTemplate.upsert(query,update, User.class);
        mongoTemplate.upsert(query,update,"mongodb_user");
        mongoTemplate.upsert(query,update, User.class,"mongodb_user");
    }

    @Test
    public void query004() {
        List<MongoDbJavaTest> list = new ArrayList<>();
        User user= new User();
        user.setId("5d1312aeb1829c279c6c256b");
        list.add(user);

        Query query = Query.query(Criteria.where("_id").in("5d1312aeb1829c279c6c256b","5d13133ab1829c29d02ce29c"));
//  根据条件删除
        mongoTemplate.remove(query);
        mongoTemplate.remove(user);
        mongoTemplate.remove(User.class);
//  根据条件删除（可删除多条）
        mongoTemplate.remove(query,User.class,"mongodb_user");
    }
}
