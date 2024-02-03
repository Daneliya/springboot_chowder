package com.xxl.excel.vo;

import lombok.Data;

import java.util.Date;

/**
 * 请求体
 *
 * @author xxl
 * @date 2024/2/3 22:36
 */
@Data
public class QueryVo {

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 查询类型
     */
    private String queryType;
}
