package com.xxl.mongodb.result;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author xxl
 * @date 2025/12/26 15:30
 */
@Data
@Document(collection = "Todo_Center")
public class TodoCenter {

    /**
     * 任务Id
     */
    @Id
    private String id;

    /**
     * 租户Id
     */
    private Integer tenantId;

    /**
     * 用户Id
     */
    private Integer userId;


    /**
     * 任务名称 --- 标题
     */
    private String taskName;


    /**
     * 任务Id
     */
    private Long taskId;


    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 任务类型名称
     */
    private String taskTypeName;


    /**
     * 任务图标
     */
    private String icon;


    /**
     * 任务描述
     */
    private String description;


    /**
     * 任务内容
     */
    private Object content;


    /**
     * 开始时间
     */
    private Date startTime;


    /**
     * 结束时间
     */
    private Date endTime;


    /**
     * 1:未处理 2:已经处理 3：已过期
     */
    private Integer status;


    /**
     * 优先级
     */
    private Integer priority;


    /**
     * 是否发送通知消息
     */
    private Boolean disableNotification;

    /**
     * 是否可以操作 【ap和pc区分是否可以操作】
     */
    private Object todoOperation;

    /**
     * URL
     */
    private String detailUrl;


    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 更新时间
     */
    private Date updateTime;


    /**
     * 创建人Id
     */
    private String creatorId;

    /**
     * 最后一次提醒时间
     */
    private int remindTimes;

    /**
     * 提醒次数
     */
    private Date lastRemindTime;
}
