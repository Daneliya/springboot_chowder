package com.xxl.thymeleaf.controller.evaluate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluateThreeContent {

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
     * 占分
     */
    private Double scorePoints;

    /**
     * 得分
     */
    private Double score;
}