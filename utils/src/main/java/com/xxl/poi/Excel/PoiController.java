package com.xxl.poi.Excel;

import cn.hutool.core.date.DateUtil;
import com.xxl.poi.Book;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: xxl
 * @Date: 2022/08/11 14:25
 */
public class PoiController {

    /**
     * 自定义导出
     *
     * @param response
     * @throws Exception
     */
    public void exportBookList(HttpServletResponse response) throws Exception {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1L, "《on java 基础卷》", "java", 10, "简介1"));
        bookList.add(new Book(1L, "《on java 进阶卷》", "Java", 10, "简介2"));


        String name = "书籍信息";
        String[] head = {"姓名", "性别", "手机号", "院校"};
        String[] context = {"name", "category", "score", "intro"};
        ExcelExportUtils.exportView2007(response, bookList, head, context, name);
    }

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
