package com.xxl.thymeleaf.controller.latest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppInfo {
    private Long id;
    private Long appId;
    private String name;
    private String price;
    private String language;
    private String version;
    private String url;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Date updateTime;
}