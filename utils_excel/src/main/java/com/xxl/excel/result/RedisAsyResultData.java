package com.xxl.excel.result;

import lombok.Data;

/**
 * redis异步结果
 *
 * @author xxl
 * @date 2024/2/4 0:57
 */
@Data
public class RedisAsyResultData {

    private boolean success;
    private String redisKey;
    private Object data;
    private boolean flag;
    private String error;

    /**
     * 当前数量
     */
    private Object done;

    /**
     * 总数
     */
    private Object total;

}
