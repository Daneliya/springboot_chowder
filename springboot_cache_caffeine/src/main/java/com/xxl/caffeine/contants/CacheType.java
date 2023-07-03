package com.xxl.caffeine.contants;

/**
 * 缓存枚举类
 *
 * @author xxl
 * @date 2023/6/5 23:04
 */
public enum CacheType {

    TEN(10),

    FIVE(5);

    private final int expires;

    CacheType(int expires) {
        this.expires = expires;
    }

    public int getExpires() {
        return expires;
    }

}
