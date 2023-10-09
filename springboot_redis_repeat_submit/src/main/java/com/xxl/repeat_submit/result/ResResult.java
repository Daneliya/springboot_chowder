package com.xxl.repeat_submit.result;

import lombok.Data;

/**
 * @author xxl
 * @date 2022/11/18 0:15
 */
@Data
public class ResResult {

    private String sysError;

    public ResResult() {

    }

    public static Object getSysError(String message) {
        return message;
    }

    public ResResult(String message) {
        this.setSysError(message);
    }

    public static ResResult getSuccess() {
        ResResult resResult = new ResResult();
        resResult.setSysError("成功");
        return resResult;
    }

}
