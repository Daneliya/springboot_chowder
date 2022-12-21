package com.xxl.security.result;

import lombok.Data;

import java.util.HashMap;

/**
 * 自定义返回结果集
 *
 * @author xxl
 * @date 2022/12/18 0:59
 */
@Data
public class ResponseResult {

    private Integer code;

    private String msg;

    private HashMap<String, String> data = new HashMap<>();

    public ResponseResult(Integer code, String msg, HashMap<String, String> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
