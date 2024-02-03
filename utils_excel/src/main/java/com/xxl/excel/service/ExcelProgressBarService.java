package com.xxl.excel.service;

import com.xxl.excel.vo.QueryVo;

/**
 * excel进度条测试 接口类
 *
 * @author xxl
 * @date 2024/2/3 22:56
 */
public interface ExcelProgressBarService {

    /**
     * 导出excel
     *
     * @param queryVo
     * @param key
     * @return
     */
    String export(QueryVo queryVo, String key);
}
