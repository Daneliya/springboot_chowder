package com.xxl.mongodb;

import cn.hutool.core.util.RandomUtil;
import com.github.javafaker.Faker;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import com.xxl.mongodb.result.SysUser;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
                        FAKER.date().birthday(),
                        RandomUtil.randomInt(100, 1000000)))
                .limit(10000)
                .collect(Collectors.toList());
        mongoTemplate.insert(sysUserList, SysUser.class);
    }

    /**
     * 添加
     */
    @Test
    public void query001() {
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

    /**
     * 查询
     */
    @Test
    public void query002() {
        // 查询 userName=xxl，结果为集合列表
        Query query = Query.query(Criteria.where("userName").is("xxl"));
        mongoTemplate.find(query, SysUser.class);
        mongoTemplate.find(query, SysUser.class, "sys_user");
        // 查询所有，结果为集合列表
        mongoTemplate.findAll(SysUser.class);
        mongoTemplate.findAll(SysUser.class, "sys_user");

        // 分页查询（page页码，pageSize每页展示几个）
        int page = 1;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Order.desc("birthday")));
        Query query2 = new Query().with(pageable);
        mongoTemplate.find(query2, SysUser.class);
        mongoTemplate.find(query2, SysUser.class, "sys_user");

        // 查询多个
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

    /**
     * 更新
     */
    @Test
    public void query003() {
        Query query = Query.query(Criteria.where("_id").is("658712b96a3c742d4070f6ca"));
        Update update = Update.update("userName", "xxl");

        // 更新一条数据
        mongoTemplate.updateFirst(query, update, SysUser.class);
        mongoTemplate.updateFirst(query, update, "sys_user");
        mongoTemplate.updateFirst(query, update, SysUser.class, "sys_user");

        // 更新多条数据
        mongoTemplate.updateMulti(query, update, SysUser.class);
        mongoTemplate.updateMulti(query, update, "sys_user");
        mongoTemplate.updateMulti(query, update, SysUser.class, "sys_user");

        // 更新数据，如果数据不存在就新增
        mongoTemplate.upsert(query, update, SysUser.class);
        mongoTemplate.upsert(query, update, "sys_user");
        mongoTemplate.upsert(query, update, SysUser.class, "sys_user");

        // 更新条件不变，更新字段改成了一个我们集合中不存在的，用set方法如果更新的key不存在则创建一个新的key
        update = Update.update("userName", "xxl").set("nickName", "xxl");
        mongoTemplate.upsert(query, update, SysUser.class);

        // update的inc方法用于做累加操作，将money在之前的基础上加上100
        update = Update.update("userName", "xxl").inc("money", 100);
        mongoTemplate.updateMulti(query, update, SysUser.class);

        // update的rename方法用于修改key的名称
        update = Update.update("userName", "xxl").rename("nickName", "nickNameNew");
        mongoTemplate.updateMulti(query, update, SysUser.class);

        // update的unset方法用于删除key
        update = Update.update("userName", "xxl").unset("nickNameNew");
        mongoTemplate.updateMulti(query, update, SysUser.class);

        // update的pull方法用于删除tags数组中的java
        List<String> tags = new ArrayList<>();
        tags.add("java");
        tags.add("python");
        update = Update.update("userName", "xxl").set("tags", tags);
        mongoTemplate.upsert(query, update, SysUser.class);
        update = Update.update("userName", "xxl").pull("tags", "java");
        mongoTemplate.updateMulti(query, update, SysUser.class);
    }

    /**
     * 删除
     */
    @Test
    public void query004() {
        // 根据条件删除（可删除多条）
        Query query = Query.query(Criteria.where("id").in("65846bbd6a3c7445686c974a", "65846bbd6a3c7445686c974b"));
        mongoTemplate.remove(query, SysUser.class); // 指定对象
        mongoTemplate.remove(query, "sys_user"); // 直接指定MongoDB集合名称
        mongoTemplate.remove(query, SysUser.class, "sys_user");
        SysUser user = new SysUser();
        user.setId("65846bbd6a3c7445686c974a");
        mongoTemplate.remove(user);

        // 删除集合，可传实体类，也可以传名称
        mongoTemplate.dropCollection(SysUser.class);
        mongoTemplate.dropCollection("sys_user");

        // 删除数据库；在开发中，开发所使用的数据库是在配置文件中配置的；使用这个方法即可直接删除配置对应的数据库
        mongoTemplate.getDb().drop();

        // 查询出符合条件的第一个结果，并将符合条件的数据删除,只会删除第一条
        query = Query.query(Criteria.where("userName").is("xxl"));
        SysUser article = mongoTemplate.findAndRemove(query, SysUser.class);
        // 查询出符合条件的所有结果，并将符合条件的所有数据删除
        query = Query.query(Criteria.where("userName").is("xxl"));
        List<SysUser> articles = mongoTemplate.findAllAndRemove(query, SysUser.class);
    }

    /**
     * 集合查询
     */
    @Test
    public void query005() {
        Query query = Query.query(Criteria.where("userName").exists(true));
        // 查询指定字段的list集合，是去重后的结果
        // entityClass：实体类，实际上就是实体类.class；如：SysUser.class
        // mongoTemplate.getCollectionName(entityClass)：可获取到entityClass实体类所对应的集合名称
        // mongoTemplate.getCollection(mongoTemplate.getCollectionName(entityClass))：可通过集合名称获取到对应集合
        // mongoTemplate.getCollection(collectionName)：返回的是基本的Driver集合对象，即DBCollection类型
        // 因此使用 getCollection() 方法获取到的集合类型，不是我们在开发过程中所使用的集合类型
        // key：指定键值，实际上就是MongoDB数据库集合中文档的字段名
        // query：查询对象
        // query.getQueryObject()：获取对应查询对象的查询条件
        // .distinct(key, query.getQueryObject())：在单个集合或视图，查询满足条件的所有文档中，指定字段的不同值
        String collectionName = mongoTemplate.getCollectionName(SysUser.class);
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        DistinctIterable<String> userName = collection.distinct("userName", query.getQueryObject(), String.class);
        List<String> userNameList = StreamSupport.stream(userName.spliterator(), false).collect(Collectors.toList());
        System.out.println(userNameList);
        // 统计去重后的数量
        int size = this.mongoTemplate.getCollection(collectionName)
                .distinct("userName", query.getQueryObject(), String.class)
                .into(new ArrayList<>())
                .size();
        System.out.println(size);
    }

    @Test
    public void query006() {
        TypedAggregation tagg = TypedAggregation.newAggregation(SysUser.class,
                Arrays.asList(
                        //筛选条件
                        TypedAggregation.match(Criteria.where("phoneNumber").regex("^17")),
                        //分组过滤条件，first，as里最后包含展示的字段
                        TypedAggregation.group("money").first("userName").as("userName")
                                .first("phoneNumber").as("phoneNumber")
                                .first("birthday").as("birthday"),
                        //挑选需要字段
                        TypedAggregation.project("userName", "phoneNumber", "birthday"),
                        //排序字段
                        TypedAggregation.sort(Sort.by(Sort.Order.desc("birthday")))
                )
        );
        AggregationResults result111 = mongoTemplate.aggregate(tagg, SysUser.class);
        List<SysUser> rd = result111.getMappedResults();
        System.out.println("排序后的mongoTemplate.group列表1：" + rd);
    }
}
