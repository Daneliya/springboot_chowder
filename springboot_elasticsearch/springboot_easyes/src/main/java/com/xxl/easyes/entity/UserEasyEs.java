package com.xxl.easyes.entity;

import cn.easyes.annotation.IndexField;
import cn.easyes.annotation.IndexId;
import cn.easyes.annotation.IndexName;
import cn.easyes.annotation.rely.Analyzer;
import cn.easyes.annotation.rely.FieldType;
import cn.easyes.annotation.rely.IdType;
import lombok.Data;

import java.util.Date;

/**
 * 用户信息ES文档实体类
 * 对应Elasticsearch中的user_easy_es索引
 *
 * @author xxl
 * @date 2026/2/24 09:22
 */
@IndexName(value = "user_easy_es")
@Data
public class UserEasyEs {

    /**
     * 主键ID
     * IdType.CUSTOMIZE: 自定义ID，由业务代码指定
     * 其他可选类型：
     * - IdType.UUID: 自动生成UUID
     * - IdType.AUTO: 自动生成雪花算法ID
     */
    @IndexId(type = IdType.CUSTOMIZE)
    private Long id;

    /**
     * 用户姓名
     * 未指定@IndexField时，Easy-Es会自动推断字段类型
     * String类型默认映射为KEYWORD
     */
    private String name;

    /**
     * 用户年龄
     * Integer类型默认映射为INTEGER
     */
    private Integer age;

    /**
     * 用户性别
     * 0: 女, 1: 男
     */
    private Integer sex;

    /**
     * 用户地址
     * FieldType.TEXT: 全文检索类型，会进行分词
     * analyzer=IK_SMART: 索引时使用IK智能分词器
     * searchAnalyzer=IK_SMART: 搜索时使用IK智能分词器
     * 分词后可对地址中的关键词进行模糊匹配查询
     */
    @IndexField(fieldType = FieldType.TEXT, analyzer = Analyzer.IK_SMART, searchAnalyzer = Analyzer.IK_SMART)
    private String address;

    /**
     * 创建时间
     * FieldType.DATE: 日期类型
     * dateFormat: 支持多种日期格式，用||分隔
     * - yyyy-MM-dd HH:mm:ss: 标准日期时间格式
     * - yyyy-MM-dd: 仅日期格式
     * - epoch_millis: 毫秒时间戳
     */
    @IndexField(fieldType = FieldType.DATE, dateFormat = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

}

