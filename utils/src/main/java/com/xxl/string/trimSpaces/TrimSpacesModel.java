package com.xxl.string.trimSpaces;

import lombok.Data;

/**
 * 空字符串过滤测试实体
 *
 * @Author: xxl
 * @Date: 2024/11/26 13:16
 */
@Data
public class TrimSpacesModel {

    private String id;

    @TrimSpaces
    private String name;

}
