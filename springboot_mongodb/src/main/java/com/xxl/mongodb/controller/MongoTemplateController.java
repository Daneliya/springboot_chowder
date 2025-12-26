package com.xxl.mongodb.controller;

import cn.hutool.core.util.ObjectUtil;
import com.xxl.mongodb.result.SysUser;
import com.xxl.mongodb.result.TodoCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxl
 * @date 2023/12/21 0:46
 */
@RestController
public class MongoTemplateController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping(value = "/test001")
    public void test001() {
        List<SysUser> sysUserList = new ArrayList<>();
        SysUser user = new SysUser();
        user.setUserName("admin");
        user.setAddress("地址");
        sysUserList.add(user);

        //  保存对象到mongodb
        mongoTemplate.save(user);
//        mongoTemplate.insert(user);
    }


    @GetMapping(value = "/test002")
    public void test002() {
        Query query = Query.query(Criteria.where("userName").is("xxl"));
        mongoTemplate.find(query, SysUser.class);
    }

    @GetMapping(value = "/test003")
    public void test003() {
        Integer index = 1;
        Integer size = 10;
        String sort = "desc";
        Integer userId = 129224;
        Integer tenantId = 332;
        Integer status = 1;
        String taskType = "TODO_INTERN_EVALUATE_STUDENT_APP";

        // 构建公共查询条件
        Criteria commonCriteria = Criteria.where("userId").is(userId)
                .and("tenantId").is(tenantId)
                .and("status").is(status)
                .and("taskType").is(taskType);

        // 总数统计（不需要排序）
        Query totalQuery = new Query(commonCriteria);
        long count = mongoTemplate.count(totalQuery, TodoCenter.class);

        // 构建分页查询对象并设置排序
        Query query = new Query(commonCriteria);
        if (ObjectUtil.isNotNull(sort) && sort.equals("desc")) {

            // 即将截止排序逻辑：
            // 第一步：按「是否超期」排序（未超期的排前面），用0/1标记，0表示未超期，1表示已超期
            // 第二步：未超期的按endTime升序（截止时间越近越前），已超期的按endTime降序（超期时间越近越前）
            Sort.Order overdueOrder = new Sort.Order(Sort.Direction.ASC,
                    "{$cond: [{ $lt: ['$endTime', new Date()] }, 1, 0]}");
            // 未超期：endTime升序（近→远）；已超期：endTime降序（近→远）
            Sort.Order endTimeOrder = new Sort.Order(
                    Sort.Direction.ASC,
                    "{$cond: [{ $lt: ['$endTime', new Date()] }, { $multiply: ['$endTime', -1] }, '$endTime']}"
            );
            // 兼容Spring Data MongoDB 2.3的排序语法
            query.with(Sort.by(overdueOrder, endTimeOrder));

        } else {
            // === 默认排序：最新到达，按创建时间倒序 ===
            query.with(Sort.by(Sort.Direction.DESC, "createTime"));
        }
        query.skip((long) (index - 1) * size);
        query.limit(size);
        List<TodoCenter> todoCenterList = mongoTemplate.find(query, TodoCenter.class);
        System.out.println(todoCenterList);
    }
}
