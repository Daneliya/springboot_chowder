package com.xxl.thymeleaf.controller.evaluate;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class EvaluateThreeItem {

    /**
     * 标题
     */
    private String title;

    /**
     * 标题
     */
    private String oneItem;

    private Integer downNum;

    private Map<String, List<EvaluateThreeContent>> evaluateContentMap;
}