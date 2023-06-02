package com.xxl.encrypt_hutool.util;

import cn.hutool.core.lang.Singleton;
import cn.hutool.core.lang.Snowflake;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/06/01 15:06
 * @Version: 1.0
 */
public class SnowIdUtils {

    private static final Snowflake SNOWFLAKE = SnowIdUtils.getSnowflake(1, 1);

    public static Long getNextId() {
        return SNOWFLAKE.nextId();
    }


    public static Snowflake getSnowflake(long workerId, long datacenterId) {
        return Singleton.get(Snowflake.class, workerId, datacenterId);
    }
}
