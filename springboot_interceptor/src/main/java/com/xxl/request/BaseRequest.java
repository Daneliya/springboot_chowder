package com.xxl.request;

import lombok.Data;

/**
 * @author xxl
 * @date 2022/12/22 22:20
 */
@Data
public class BaseRequest {

    /**
     * 医院id
     */
    private Integer hospitalId;

//    public BaseRequest(Integer hospitalId) {
//        this.hospitalId = hospitalId;
//    }
//
//    public Integer getHospitalId() {
//        return hospitalId;
//    }
}
