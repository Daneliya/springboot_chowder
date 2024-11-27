package com.xxl.thymeleaf.controller.latest;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class AppPriceInfoVO {
    private Integer upNum;
    private Integer downNum;
    private String mailTime;
    private Map<String, List<AppInfo>> upPriceMap;
    private Map<String, List<AppInfo>> downPriceMap;
}