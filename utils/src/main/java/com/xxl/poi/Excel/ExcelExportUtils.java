package com.xxl.poi.Excel;

import org.apache.commons.beanutils.*;
import org.apache.commons.beanutils.converters.ConverterFacade;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description: excel导出公用接口
 *
 * @Auther: xxl
 */
public class ExcelExportUtils extends ConvertUtilsBean {

    /**
     * @描述 :简单样式
     * @参数: ExcelEntity object, OutputStream outStream
     * @返回值: void
     * @author: xxl
     * @创建时间: 2018/8/14 - 16:22
     */
    @SuppressWarnings("rawtypes")
    public static void exportExcel(ExcelEntity object, OutputStream outStream) throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(object.getSheetName());
        HSSFCellStyle sheetStyle1 = wb.createCellStyle();//标题样式
        sheetStyle1.setAlignment(HorizontalAlignment.CENTER);//居中
        sheetStyle1.setBorderTop(BorderStyle.THIN);//边框:上下左右
        sheetStyle1.setBorderBottom(BorderStyle.THIN);
        sheetStyle1.setBorderLeft(BorderStyle.THIN);
        sheetStyle1.setBorderRight(BorderStyle.THIN);
        HSSFFont font = wb.createFont();
        font.setFontName("仿宋");
        font.setBold(true);//粗体显示
        font.setFontHeightInPoints((short) 12);
        sheetStyle1.setFont(font);
        HSSFCellStyle sheetStyle2 = wb.createCellStyle();//内容样式
        sheetStyle2.setAlignment(HorizontalAlignment.CENTER);//居中
        sheetStyle2.setBorderTop(BorderStyle.THIN);//边框:上下左右
        sheetStyle2.setBorderBottom(BorderStyle.THIN);
        sheetStyle2.setBorderLeft(BorderStyle.THIN);
        sheetStyle2.setBorderRight(BorderStyle.THIN);
        HSSFRow row = sheet.createRow(0);// 创建第一行
        HSSFCell cell = row.createCell(0);// 创建第一行的第一个单元格
        //单元格锁定的样式
        HSSFCellStyle lockstyle = wb.createCellStyle();
        lockstyle.setHidden(true);
        //lockstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //lockstyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        cell.setCellValue("序号");
        cell.setCellStyle(sheetStyle1);
        String[] colNames = object.getColumnNames();
        String[] propertys = object.getPropertyNames();
        HSSFCell hcell = null;
        for (int i = 0; i < colNames.length; i++) { // 添加列名，从第一行的第二个单元格开始添加
            hcell = row.createCell(i + 1);
            hcell.setCellStyle(sheetStyle1);
            sheet.setColumnWidth(i + 1, 5000);
            if ("id".equals(colNames[i])) {
                hcell.setCellStyle(lockstyle);
                sheet.setColumnWidth(i + 1, 0);
            }
            hcell.setCellValue(colNames[i]);
        }
        Iterator it = object.getResultList().iterator();
        Object obj = null;
        if (object.getResultList().size() > 0) {
            obj = object.getResultList().get(0);
        }
        int rowNum = 1; // 从第二行开始添加数据
        Map map = null;
        while (it.hasNext()) {
            if (obj instanceof Map) {
                map = (Map) it.next();
            } else {
                map = (Map) ExcelExportUtils.convertBeanToMap(it.next());
            }
            HSSFRow rw = sheet.createRow(rowNum);
            hcell = rw.createCell(0);
            hcell.setCellStyle(sheetStyle2);
            hcell.setCellValue(rowNum); // 添加序号
            rowNum++;
            for (int x = 0; x < propertys.length; x++) {
                String property = propertys[x];
                if (map.containsKey(property)) {
                    Object value = map.get(propertys[x]); // 根据属性名称得到属性值
                    if (property.equals("sex")) {
                        String sexStr = (String) value;
                        switch (sexStr) {
                            case "1":
                                value = "男";
                                break;
                            case "2":
                                value = "女";
                                break;
                            case "3":
                                value = "其他";
                                break;
                        }
                    } else if (property.equals("cengji")) {
                        Integer taskProperty = (Integer) value;
                        switch (taskProperty) {
                            case 1:
                                value = "本科";
                                break;
                            case 2:
                                value = "专科";
                                break;
                            case 3:
                                value = "职高";
                                break;
                            case 4:
                                value = "中专";
                                break;
                            case 5:
                                value = "技校";
                                break;
                            default:
                                value = "无";
                        }
                    } else {
                        System.out.println("");
                    }
                    if (value == null || "null".equalsIgnoreCase(value.toString())) {
                        value = "";
                    }
                    hcell = rw.createCell(x + 1);
                    hcell.setCellStyle(sheetStyle2);
                    hcell.setCellValue(value + "");
                } else {
                    hcell = rw.createCell(x + 1);
                    hcell.setCellValue("");
                    hcell.setCellStyle(sheetStyle2);
                }
            }
        }
        try {
            wb.write(outStream);
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            if (outStream != null) {
                outStream.close();
            }
            e.printStackTrace();
        }
    }

    /**
     * @描述 :简单样式
     * @参数: ExcelEntity object, OutputStream outStream
     * @返回值: void
     */
    @SuppressWarnings("rawtypes")
    public static void exportExcelStatistics(ExcelEntity object, OutputStream outStream) throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(object.getSheetName());
        //标题样式
        HSSFCellStyle sheetStyle1 = wb.createCellStyle();
        //居中
        sheetStyle1.setAlignment(HorizontalAlignment.CENTER);
        //边框:上下左右
        sheetStyle1.setBorderTop(BorderStyle.THIN);
        sheetStyle1.setBorderBottom(BorderStyle.THIN);
        sheetStyle1.setBorderLeft(BorderStyle.THIN);
        sheetStyle1.setBorderRight(BorderStyle.THIN);
        HSSFFont font = wb.createFont();
        font.setFontName("仿宋");
        font.setBold(true);//粗体显示
        font.setFontHeightInPoints((short) 12);
        sheetStyle1.setFont(font);
        HSSFCellStyle sheetStyle2 = wb.createCellStyle();//内容样式
        sheetStyle2.setAlignment(HorizontalAlignment.CENTER);//居中
        sheetStyle2.setBorderTop(BorderStyle.THIN);//边框:上下左右
        sheetStyle2.setBorderBottom(BorderStyle.THIN);
        sheetStyle2.setBorderLeft(BorderStyle.THIN);
        sheetStyle2.setBorderRight(BorderStyle.THIN);
        HSSFRow row = sheet.createRow(0);// 创建第一行
//        HSSFCell cell = row.createCell(0);// 创建第一行的第一个单元格
        //单元格锁定的样式
        HSSFCellStyle lockstyle = wb.createCellStyle();
        lockstyle.setHidden(true);
        String[] colNames = object.getColumnNames();
        String[] propertys = object.getPropertyNames();
        HSSFCell hcell = null;
        for (int i = 0; i < colNames.length; i++) { // 添加列名，从第一行的第二个单元格开始添加
            hcell = row.createCell(i);
            hcell.setCellStyle(sheetStyle1);
            sheet.setColumnWidth(i, 5000);
            hcell.setCellValue(colNames[i]);
        }
        Iterator it = object.getResultList().iterator();
        Object obj = null;
        if (object.getResultList().size() > 0) {
            obj = object.getResultList().get(0);
        }
        int rowNum = 1; // 从第二行开始添加数据
        Map map = null;
        while (it.hasNext()) {
            if (obj instanceof Map) {
                map = (Map) it.next();
            } else {
                map = (Map) ExcelExportUtils.convertBeanToMap(it.next());
            }
            HSSFRow rw = sheet.createRow(rowNum);
            rowNum++;
            for (int x = 0; x < propertys.length; x++) {
                String property = propertys[x];
                if (map.containsKey(property)) {
                    Object value = map.get(propertys[x]); // 根据属性名称得到属性值

                    if (value == null || "null".equalsIgnoreCase(value.toString())) {
                        value = "";
                    }
                    hcell = rw.createCell(x);
                    hcell.setCellStyle(sheetStyle2);
                    hcell.setCellValue(value + "");
                } else {
                    hcell = rw.createCell(x);
                    hcell.setCellValue("");
                    hcell.setCellStyle(sheetStyle2);
                }
            }
        }
        try {
            wb.write(outStream);
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            if (outStream != null) {
                outStream.close();
            }
            e.printStackTrace();
        }
    }

    @SuppressWarnings("rawtypes")
    public static void exportExcelStatistics2007(ExcelEntity object, OutputStream outStream) throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(object.getSheetName());
        XSSFCellStyle sheetStyle1 = wb.createCellStyle();//标题样式
        sheetStyle1.setAlignment(HorizontalAlignment.CENTER);
        sheetStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
        sheetStyle1.setBorderRight(BorderStyle.THIN);
        sheetStyle1.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        sheetStyle1.setBorderLeft(BorderStyle.THIN);
        sheetStyle1.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        sheetStyle1.setBorderTop(BorderStyle.THIN);
        sheetStyle1.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        sheetStyle1.setBorderBottom(BorderStyle.THIN);
        sheetStyle1.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        //背景颜色
        sheetStyle1.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        //填充图案
        sheetStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont headerFont = wb.createFont();
//        font.setFontName("仿宋");
//        font.setBold(true);//粗体显示
//        font.setFontHeightInPoints((short) 12);
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        sheetStyle1.setFont(headerFont);

        //内容样式
        XSSFCellStyle sheetStyle2 = wb.createCellStyle();
        sheetStyle2.setAlignment(HorizontalAlignment.CENTER);//居中
        sheetStyle2.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        sheetStyle2.setBorderTop(BorderStyle.THIN);//边框:上下左右
        sheetStyle2.setBorderBottom(BorderStyle.THIN);
        sheetStyle2.setBorderLeft(BorderStyle.THIN);
        sheetStyle2.setBorderRight(BorderStyle.THIN);
        //背景颜色
        sheetStyle2.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        //填充图案
        sheetStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        sheetStyle2.setFont(dataFont);

        XSSFRow row = sheet.createRow(0);// 创建第一行
//        HSSFCell cell = row.createCell(0);// 创建第一行的第一个单元格
        //单元格锁定的样式
        XSSFCellStyle lockstyle = wb.createCellStyle();
        lockstyle.setHidden(true);
        String[] colNames = object.getColumnNames();
        String[] propertys = object.getPropertyNames();
        XSSFCell xcell = null;
        for (int i = 0; i < colNames.length; i++) { // 添加列名，从第一行的第二个单元格开始添加
            xcell = row.createCell(i);
            xcell.setCellStyle(sheetStyle1);
            sheet.setColumnWidth(i, 5000);
            //默认行高
            sheet.setDefaultRowHeight((short) 600);
            //自适应列宽
            sheet.autoSizeColumn(i);
            xcell.setCellValue(colNames[i]);
        }
        Iterator it = object.getResultList().iterator();
        Object obj = null;
        if (object.getResultList().size() > 0) {
            obj = object.getResultList().get(0);
        }
        int rowNum = 1; // 从第二行开始添加数据
        Map map = null;
        while (it.hasNext()) {
            if (obj instanceof Map) {
                map = (Map) it.next();
            } else {
                map = (Map) ExcelExportUtils.convertBeanToMap(it.next());
            }
            XSSFRow rw = sheet.createRow(rowNum);
            rowNum++;
            for (int x = 0; x < propertys.length; x++) {
                String property = propertys[x];
                if (map.containsKey(property)) {
                    Object value = map.get(propertys[x]); // 根据属性名称得到属性值

                    if (value == null || "null".equalsIgnoreCase(value.toString())) {
                        value = "";
                    }
                    xcell = rw.createCell(x);
                    xcell.setCellStyle(sheetStyle2);
                    xcell.setCellValue(value + "");
                } else {
                    xcell = rw.createCell(x);
                    xcell.setCellValue("");
                    xcell.setCellStyle(sheetStyle2);
                }
            }
        }
        try {
            wb.write(outStream);
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            if (outStream != null) {
                outStream.close();
            }
            e.printStackTrace();
        }
    }

    /**
     * @描述 :简单样式
     * @参数: ExcelEntity object, OutputStream outStream
     * @返回值: void
     */
    @SuppressWarnings("rawtypes")
    public static void exportExcelMergeCells(ExcelEntity object, String[] colNames1, String type, OutputStream outStream) throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(object.getSheetName());
        HSSFCellStyle sheetStyle1 = wb.createCellStyle();//标题样式
        sheetStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
        sheetStyle1.setAlignment(HorizontalAlignment.CENTER);//居中
        sheetStyle1.setBorderTop(BorderStyle.THIN);//边框:上下左右
        sheetStyle1.setBorderBottom(BorderStyle.THIN);
        sheetStyle1.setBorderLeft(BorderStyle.THIN);
        sheetStyle1.setBorderRight(BorderStyle.THIN);
        HSSFFont font = wb.createFont();
        font.setFontName("仿宋");
        font.setBold(true);//粗体显示
        font.setFontHeightInPoints((short) 12);
        sheetStyle1.setFont(font);
        HSSFCellStyle sheetStyle2 = wb.createCellStyle();//内容样式
        sheetStyle2.setAlignment(HorizontalAlignment.CENTER);//居中
        sheetStyle2.setBorderTop(BorderStyle.THIN);//边框:上下左右
        sheetStyle2.setBorderBottom(BorderStyle.THIN);
        sheetStyle2.setBorderLeft(BorderStyle.THIN);
        sheetStyle2.setBorderRight(BorderStyle.THIN);
        HSSFRow row = sheet.createRow(0);// 创建第一行
//        HSSFCell cell = row.createCell(0);// 创建第一行的第一个单元格
        //单元格锁定的样式
//        HSSFCellStyle lockstyle = wb.createCellStyle();
//        lockstyle.setHidden(true);

//        cell.setCellValue("序号");
//        cell.setCellStyle(sheetStyle1);
        String[] colNames = object.getColumnNames();
        String[] propertys = object.getPropertyNames();
        HSSFCell hcell = null;
        for (int i = 0; i < colNames.length; i++) { // 添加列名，从第一行的第二个单元格开始添加
            hcell = row.createCell(i);
            hcell.setCellStyle(sheetStyle1);
            sheet.setColumnWidth(i, 5000);
//            if ("id".equals(colNames[i])) {
//                hcell.setCellStyle(lockstyle);
//                sheet.setColumnWidth(i + 1, 0);
//            }
            hcell.setCellValue(colNames[i]);
        }

        HSSFCell row3Cell = null;
        HSSFRow row3 = sheet.createRow(1);
//        row3.setHeight((short) 800);
        for (int i = 0; i < colNames1.length; i++) {
            row3Cell = row3.createCell(i);
            row3Cell.setCellStyle(sheetStyle1);
            row3Cell.setCellValue(colNames1[i]);
        }
        switch (type) {
            case "hosTeaching":
                // 合并单元格
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 4));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 6));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 8));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 10));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 12));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 13, 14));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 15, 16));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 17, 18));

                sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
                break;
            case "hosteacTask":
                // 合并单元格
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 6, 7));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 9));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 10, 11));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 12, 13));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 14, 15));

                sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
                break;
            case "hosteacPerformTask":
                // 合并单元格
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 4));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 7));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 10));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 13));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 14, 16));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 17, 19));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 20, 22));

                sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
                break;
            case "hosTeachingTask":
                // 合并单元格
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 6, 7));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 9));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 10, 11));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 12, 13));

                sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));

                break;
            case "uniTeachingClinical":
// 合并单元格
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 4));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 6));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 8));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 10));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 12));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 13, 14));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 15, 16));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 17, 18));

                sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
                break;
            case "unihosTeachingStatistics":

                sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 6));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 8));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 10));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 12));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 13, 14));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 15, 16));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 17, 18));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 19, 20));

                sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 4, 4));
                break;
            case "uniStuStatistics":
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 8));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 10));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 12));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 13, 14));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 15, 16));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 17, 18));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 19, 20));

                sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 4, 4));
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 5, 5));
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 6, 6));
                break;

        }


        Iterator it = object.getResultList().iterator();
        Object obj = null;
        if (object.getResultList().size() > 0) {
            obj = object.getResultList().get(0);
        }
        int rowNum = 2; // 从第二行开始添加数据
        Map map = null;
        while (it.hasNext()) {
            if (obj instanceof Map) {
                map = (Map) it.next();
            } else {
                map = (Map) ExcelExportUtils.convertBeanToMap(it.next());
            }
            HSSFRow rw = sheet.createRow(rowNum);
//            hcell = rw.createCell(0);
//            hcell.setCellStyle(sheetStyle2);
//            hcell.setCellValue(rowNum); // 添加序号
            rowNum++;
            for (int x = 0; x < propertys.length; x++) {
                String property = propertys[x];
                if (map.containsKey(property)) {
                    Object value = map.get(propertys[x]); // 根据属性名称得到属性值

                    if (value == null || "null".equalsIgnoreCase(value.toString())) {
                        value = "";
                    }
                    hcell = rw.createCell(x);
                    hcell.setCellStyle(sheetStyle2);
                    hcell.setCellValue(value + "");
                } else {
                    hcell = rw.createCell(x);
                    hcell.setCellValue("");
                    hcell.setCellStyle(sheetStyle2);
                }
            }
        }
        try {
            wb.write(outStream);
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            if (outStream != null) {
                outStream.close();
            }
            e.printStackTrace();
        }
    }

    /**
     * 导出Excel
     *
     * @param response
     * @param list
     * @param columns
     * @param propertyNames
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static void exportView(HttpServletResponse response, List<?> list, String[] columns, String[] propertyNames, String excelName) throws IOException, UnsupportedEncodingException, Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        ExcelEntity entity = new ExcelEntity();
        entity.setColumnNames(columns);
        entity.setPropertyNames(propertyNames);
        entity.setResultList(list);
        entity.setSheetName(sdf.format(new Date()));
        OutputStream outStream = response.getOutputStream();
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        String fileName = excelName + sdf.format(new Date()) + ".xls";
//        response.setCharacterEncoding("utf-8");
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//        response.addHeader("Pargam", "no-cache");
//        response.addHeader("Cache-Control", "no-cache");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "iso8859-1"));
        exportExcelStatistics(entity, outStream);
    }

    /**
     * 导出xlsx 文件，2007之后的版本
     *
     * @param response
     * @param list
     * @param columns
     * @param propertyNames
     * @param excelName
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static void exportView2007(HttpServletResponse response, List<?> list, String[] columns, String[] propertyNames, String excelName) throws IOException, UnsupportedEncodingException, Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        ExcelEntity entity = new ExcelEntity();
        entity.setColumnNames(columns);
        entity.setPropertyNames(propertyNames);
        entity.setResultList(list);
        entity.setSheetName(sdf.format(new Date()));
        OutputStream outStream = response.getOutputStream();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = excelName + sdf.format(new Date()) + ".xlsx";
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "iso8859-1"));
        exportExcelStatistics2007(entity, outStream);
    }


    /**
     * 导出Excel
     *
     * @param response
     * @param list
     * @param columns
     * @param propertyNames
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static void exportViewStatistics(HttpServletResponse response, List<?> list, String[] columns1, String[] columns, String[] propertyNames, String excelName, String type) throws IOException, UnsupportedEncodingException, Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        ExcelEntity entity = new ExcelEntity();
        entity.setColumnNames(columns);
        entity.setPropertyNames(propertyNames);
        entity.setResultList(list);
        entity.setSheetName(sdf.format(new Date()));
        OutputStream outStream = response.getOutputStream();
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        String fileName = excelName + sdf.format(new Date()) + ".xls";
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "iso8859-1"));
        if (type.equals("hosTeaching") || type.equals("hosteacTask") || type.equals("hosteacPerformTask") || type.equals("hosTeachingTask")
                || type.equals("uniTeachingClinical") || type.equals("unihosTeachingStatistics") || type.equals("uniStuStatistics")) {
            exportExcelMergeCells(entity, columns1, type, outStream);
        } else {
            exportExcelStatistics(entity, outStream);
        }

    }

    /**
     * 创建通用EXCEL头部
     *
     * @param headString 头部显示的字符
     * @param colSum     该报表的列数
     */
    @SuppressWarnings("deprecation")
    public void createNormalHead(String headString, int colSum, String sheetName) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);
        HSSFRow row = sheet.createRow(0);

        // 设置第一行
        HSSFCell cell = row.createCell(0);
        row.setHeight((short) 400);

        // 定义单元格为字符串类型
        //cell.setCellType(HSSFCell.ENCODING_UTF_16);
        cell.setCellType(CellType.forInt(HSSFCell.ENCODING_UTF_16));
        cell.setCellValue(new HSSFRichTextString(headString));

        // 指定合并区域
        sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) colSum));
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER); // 指定单元格居中对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 指定单元格垂直居中对齐
        cellStyle.setWrapText(true);// 指定单元格自动换行

        // 设置单元格字体
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeight((short) 300);
        cellStyle.setFont(font);

        cell.setCellStyle(cellStyle);
    }

    /**
     * @描述 :bean转Map工具
     * @参数: Object
     * @返回值: Map
     */
//    public static Map<String, Object> convertBeanToMap(Object bean) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
//        Class type = bean.getClass();
//        Map<String, Object> returnMap = new HashMap<String, Object>();
//        BeanInfo beanInfo = Introspector.getBeanInfo(type);
//        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//        for (int i = 0; i < propertyDescriptors.length; i++) {
//            PropertyDescriptor descriptor = propertyDescriptors[i];
//            String propertyName = descriptor.getName();
//            if (!propertyName.equals("class")) {
//                Method readMethod = descriptor.getReadMethod();
//                Object result = readMethod.invoke(bean, new Object[0]);
//                if (result != null) {
//                    returnMap.put(propertyName, result);
//                } else {
//                    returnMap.put(propertyName, "");
//                }
//            }
//        }
//        return returnMap;
//    }

    /**
     * bean转Map工具
     *
     * @param obj
     * @return
     */
    public static Map<?, ?> convertBeanToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            BeanUtilsBean beanUtilsBean = new BeanUtilsBean(new ExtConvertUtilsBean());
            //日期转换器，处理时间格式
            DateConverter converter = new DateConverter();
            //设置日期格式
            converter.setPattern("yyyy-MM-dd HH:mm:ss");
            //注册到BeanUtilsBean中
            beanUtilsBean.getConvertUtils().register(new ConverterFacade(converter), Date.class);
            //封装数据
            return beanUtilsBean.describe(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    //==================================== 工作簿返回 ============================================

    public static XSSFWorkbook exportView2007(List<?> list, String[] columns, String[] propertyNames) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        ExcelEntity entity = new ExcelEntity();
        entity.setColumnNames(columns);
        entity.setPropertyNames(propertyNames);
        entity.setResultList(list);
        entity.setSheetName(sdf.format(new Date()));
        return exportExcelStatistics2007(entity);
    }

    private static XSSFWorkbook exportExcelStatistics2007(ExcelEntity object) throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(object.getSheetName());
        XSSFCellStyle sheetStyle1 = wb.createCellStyle();//标题样式
        sheetStyle1.setAlignment(HorizontalAlignment.CENTER);
        sheetStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
        sheetStyle1.setBorderRight(BorderStyle.NONE);
        sheetStyle1.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        sheetStyle1.setBorderLeft(BorderStyle.NONE);
        sheetStyle1.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        sheetStyle1.setBorderTop(BorderStyle.NONE);
        sheetStyle1.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        sheetStyle1.setBorderBottom(BorderStyle.NONE);
        sheetStyle1.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        sheetStyle1.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        sheetStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        sheetStyle1.setFont(headerFont);

        //内容样式
        XSSFCellStyle sheetStyle2 = wb.createCellStyle();
        sheetStyle2.setAlignment(HorizontalAlignment.CENTER);//居中
        sheetStyle2.setBorderTop(BorderStyle.NONE);//边框:上下左右
        sheetStyle2.setBorderBottom(BorderStyle.NONE);
        sheetStyle2.setBorderLeft(BorderStyle.NONE);
        sheetStyle2.setBorderRight(BorderStyle.NONE);
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        sheetStyle2.setFont(dataFont);

        XSSFRow row = sheet.createRow(0);// 创建第一行
//        HSSFCell cell = row.createCell(0);// 创建第一行的第一个单元格
        //单元格锁定的样式
        XSSFCellStyle lockstyle = wb.createCellStyle();
        lockstyle.setHidden(true);
        String[] colNames = object.getColumnNames();
        String[] propertys = object.getPropertyNames();
        XSSFCell xcell = null;
        for (int i = 0; i < colNames.length; i++) { // 添加列名，从第一行的第二个单元格开始添加
            xcell = row.createCell(i);
            xcell.setCellStyle(sheetStyle1);
            sheet.setColumnWidth(i, 5000);
            xcell.setCellValue(colNames[i]);
        }
        Iterator it = object.getResultList().iterator();
        Object obj = null;
        if (object.getResultList().size() > 0) {
            obj = object.getResultList().get(0);
        }
        int rowNum = 1; // 从第二行开始添加数据
        Map map = null;
        while (it.hasNext()) {
            if (obj instanceof Map) {
                map = (Map) it.next();
            } else {
                map = (Map) ExcelExportUtils.convertBeanToMap(it.next());
            }
            XSSFRow rw = sheet.createRow(rowNum);
            rowNum++;
            for (int x = 0; x < propertys.length; x++) {
                String property = propertys[x];
                if (map.containsKey(property)) {
                    Object value = map.get(propertys[x]); // 根据属性名称得到属性值

                    if (value == null || "null".equalsIgnoreCase(value.toString())) {
                        value = "";
                    }
                    xcell = rw.createCell(x);
                    xcell.setCellStyle(sheetStyle2);
                    xcell.setCellValue(value + "");
                } else {
                    xcell = rw.createCell(x);
                    xcell.setCellValue("");
                    xcell.setCellStyle(sheetStyle2);
                }
            }
        }
        return wb;
    }


}
