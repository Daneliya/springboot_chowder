package com.xxl.easyexcel.model;

import lombok.Data;

import java.util.List;

/**
 * @Author: xxl
 * @Date: 2024/5/16 下午2:42
 */
@Data
public class ExcelHeader {

    private List<String> headers;

    public ExcelHeader(List<String> headers) {
        this.headers = headers;
    }

    // getter and setter
}