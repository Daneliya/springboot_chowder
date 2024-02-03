package com.xxl.excel.result;

import lombok.Builder;

/**
 * 响应
 *
 * @author xxl
 * @date 2024/2/3 23:17
 */
@Builder
public class RestBuilders extends RestMessage {

    public static SuccessBuilder successBuilder() {
        return new SuccessBuilder();
    }
}
