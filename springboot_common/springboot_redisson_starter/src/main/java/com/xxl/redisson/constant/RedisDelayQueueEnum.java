package com.xxl.redisson.redisson.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 延迟队列业务枚举
 *
 * @author xxl
 * @date 2023/09/15
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RedisDelayQueueEnum {

    SMS_PARENT_TIMEOUT("SMS_PARENT_TIMEOUT", "短信发送失败，重新发送", "orderPaymentTimeout"),
    LEAVE_BACK_SMS_TIMEOUT("LEAVE_BACK_SMS_TIMEOUT", "销假提醒短信发送", "leaveBackSmsTimeout"),
    ACTIVITY_EXAM_TIMEOUT("ACTIVITY_EXAM_TIMEOUT", "理论考试短信提醒", "activityExamTimeout"),
    ACTIVITY_SKILL_TIMEOUT("ACTIVITY_SKILL_TIMEOUT", "技能考核短信提醒", "activitySkillTimeout");

    /**
     * 延迟队列 Redis Key
     */
    private String code;

    /**
     * 中文描述
     */
    private String name;

    /**
     * 延迟队列具体业务实现的 Bean
     * 可通过 Spring 的上下文获取
     */
    private String beanId;

}