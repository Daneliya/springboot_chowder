package com.xxl.mongodb;

import com.xxl.mongodb.request.SysUserRequest;
import com.xxl.mongodb.result.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author xxl
 * @date 2023/12/25 23:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CriteriaTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Criteria查询
     */
    @Test
    public void query001() {
        SysUserRequest sysUserRequest = new SysUserRequest();
        sysUserRequest.setUserName("xxl");
        sysUserRequest.setPhoneNumber("15500000000");
        // 创建条件对象
        // criteria可以用and连接条件也可以用where连接条件
        //criteria.and("user_name").is(sysUserRequest.getUserName()).and("platformId").is(params.getPlatformId()).and("id").is(params.getId());
        //Criteria.where("birthday").gte(sysUserRequest.getBeginTime()).lte(sysUserRequest.getEndTime());

        Criteria criteria = new Criteria();
        // 1. 全等于 (手机号全字匹配)
        if (StringUtils.isNotBlank(sysUserRequest.getPhoneNumber())) {
            criteria.and("phone_number").is(sysUserRequest.getPhoneNumber());
        }
        // 2. 模糊查询 (名称模糊搜索)
        if (StringUtils.isNotBlank(sysUserRequest.getUserName())) {
            criteria.and("user_name").regex(Pattern.compile("^.*" + sysUserRequest.getUserName() + ".*$", Pattern.CASE_INSENSITIVE));
        }
        // 3. 单个条件查询多个字段
        if (StringUtils.isNotEmpty(sysUserRequest.getUserName())) {
            criteria.orOperator(
                    Criteria.where("user_name").is(sysUserRequest.getUserName()),
                    Criteria.where("nick_name").in(sysUserRequest.getUserName())
            );
        }
        // 4. 日期范围
        if (StringUtils.isNotEmpty(sysUserRequest.getBeginTime()) && StringUtils.isNotEmpty(sysUserRequest.getEndTime())) {
            criteria.andOperator(Criteria.where("birthday").gte(sysUserRequest.getBeginTime()), Criteria.where("birthday").lte(sysUserRequest.getEndTime()));
        }
        // 5. 数值范围 (存款总金额)
        if (sysUserRequest.getLowestMoney() != null && sysUserRequest.getHighestMoney() != null) {
            criteria.and("money").gte(sysUserRequest.getLowestMoney()).lte(sysUserRequest.getHighestMoney());
        }
        if (sysUserRequest.getTags() != null && !CollectionUtils.isEmpty(Arrays.asList(sysUserRequest.getTags()))) {
            if ("any".equals(sysUserRequest.getTagScope())) {
                // 6. 数组字段满足任一
                criteria.and("tags").in(sysUserRequest.getTags());
            } else if ("all".equals(sysUserRequest.getTagScope())) {
                //  7. 数组字段满足全部 (客户标签)
                criteria.and("tags").all(sysUserRequest.getTags());
            }
        }

        Query query = new Query();
        query.addCriteria(criteria);
        // 8. 查询返回指定字段 (自定义列表)
        query.fields().include("user_name");
        // 10. 分页
        query.with(PageRequest.of(sysUserRequest.getPageNum() - 1, sysUserRequest.getPageSize(),
                // 11. 排序
                Sort.by(Sort.Order.desc("birthday"))));
        // 分页（方式二，使用skip+limit）
        query.with(Sort.by(Sort.Order.desc("birthday")))
                .skip((long) (sysUserRequest.getPageNum() - 1) * sysUserRequest.getPageSize())
                .limit(sysUserRequest.getPageSize());
        // 执行查询
        List<SysUser> list = mongoTemplate.find(query, SysUser.class);
        // 12. 总记录数
        long total = mongoTemplate.count(query, SysUser.class);
    }
}
