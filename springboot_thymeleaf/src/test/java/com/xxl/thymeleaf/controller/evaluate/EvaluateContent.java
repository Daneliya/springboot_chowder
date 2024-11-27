package com.xxl.thymeleaf.controller.evaluate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluateContent {

    /**
     * 一级项目
     */
    private String oneItem;

    /**
     * 二级项目
     */
    private String twoItem;

    /**
     * 二级分值
     */
    private Double twoScore;

    /**
     * 标准内容
     */
    private String content;

    /**
     * 分值
     */
    private Double score;
}