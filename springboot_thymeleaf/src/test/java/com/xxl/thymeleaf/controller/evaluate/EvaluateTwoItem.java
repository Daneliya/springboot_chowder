package com.xxl.thymeleaf.controller.evaluate;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EvaluateTwoItem {

    /**
     * 标题
     */
    private String title;

    /**
     * 时间
     */
    private String time;

    /**
     * 租户
     */
    private String tenantName;

    /**
     * logo
     */
    private String logo;

    /**
     * 二维码
     */
    private String qrcode;

    /**
     * 被评价人
     */
    private String evaluateUserName;

    /**
     * 职称
     */
    private String jobTitleName;

    /**
     * 科室
     */
    private String deptName;

    /**
     * 内容
     */
    private List<EvaluateTwoContent> evaluateContentList;
}