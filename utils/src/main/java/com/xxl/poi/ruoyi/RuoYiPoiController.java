package com.xxl.poi.ruoyi;

import cn.hutool.core.date.DateUtil;
import com.xxl.poi.Book;
import com.xxl.poi.ruoyi.utils.excel.RuoYiExcelUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: xxl
 * @Date: 2022/09/16 16:09
 */
public class RuoYiPoiController {

    /**
     * 若依导出
     *
     * @param response
     * @throws IOException
     */
    public void exportBookListByRuoYi(HttpServletResponse response) throws IOException {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1L, "《on java 基础卷》", "java", 10, "简介1"));
        bookList.add(new Book(1L, "《on java 进阶卷》", "Java", 10, "简介2"));

        RuoYiExcelUtil<Book> util = new RuoYiExcelUtil<>(Book.class);
        util.exportExcel(response, bookList, "书籍信息" + DateUtil.format(new Date(), "yyyyMMddHHmmssSSS"));
    }
}
