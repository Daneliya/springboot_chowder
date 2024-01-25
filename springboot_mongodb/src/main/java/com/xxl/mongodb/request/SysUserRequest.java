package com.xxl.mongodb.request;

import lombok.Data;

/**
 * @author xxl
 * @date 2023/12/25 23:11
 */
@Data
public class SysUserRequest {

    private String userName;

    private String phoneNumber;

    private String beginTime;

    private String endTime;

    private Double lowestMoney;

    private Double highestMoney;

    private String TagScope;

    private String[] tags;

    private Integer pageNum = 1;

    private Integer pageSize = 10;

}
