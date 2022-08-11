package com.xxl.poi;

import com.xxl.poi.Excel.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 导出测试实体
 *
 * @Author: xxl
 * @Date: 2022/08/11 11:56
 */
@Data
@AllArgsConstructor
public class Book {

    /**
     * id
     */
    private Long id;

    /**
     * 书名
     */
    @Excel(name = "书名", sort = 1, type = Excel.Type.EXPORT)
    private String name;

    /**
     * 分类
     */
    @Excel(name = "分类", sort = 2, type = Excel.Type.EXPORT)
    private String category;

    /**
     * 评分
     */
    @Excel(name = "评分", sort = 3, type = Excel.Type.EXPORT)
    private Integer score;

    /**
     * 简介
     */
    @Excel(name = "简介", sort = 4, type = Excel.Type.EXPORT, width = 30)
    private String intro;
}
