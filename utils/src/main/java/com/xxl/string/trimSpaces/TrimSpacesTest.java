package com.xxl.string.trimSpaces;

import cn.hutool.core.util.IdUtil;

/**
 * 空字符串过滤测试
 *
 * @Author: xxl
 * @Date: 2024/11/26 13:17
 */
public class TrimSpacesTest {

    public static void main(String[] args) {
        TrimSpacesModel trimSpacesModel = new TrimSpacesModel();
        trimSpacesModel.setId(IdUtil.simpleUUID());
        trimSpacesModel.setName(" hello world ! ");
        System.out.println(trimSpacesModel);
    }
}
