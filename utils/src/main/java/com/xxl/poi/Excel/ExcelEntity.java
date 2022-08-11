package com.xxl.poi.Excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import java.util.List;

/**
 * Copyright (C), 2009-2022
 * FileName: ExcelEntity
 * Author:   xxl
 * Date:     2022/8/11 14:35
 * Description: excel参数实体类
 * Version:1.0
 */
public class ExcelEntity {

    /**
     * excel名称
     */
    private String sheetName;

    /**
     * 列名
     */
    private String[] columnNames;

    /**
     * 属性名称
     */
    private String[] propertyNames;

    private String[] cLabels;

    private int rpp = 200;

    private HSSFCellStyle style = null;

    @SuppressWarnings("rawtypes")
    private List resultList;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public String[] getPropertyNames() {
        return propertyNames;
    }

    public void setPropertyNames(String[] propertyNames) {
        this.propertyNames = propertyNames;
    }

    public String[] getCLabels() {
        return cLabels;
    }

    public void setCLabels(String[] labels) {
        cLabels = labels;
    }

    public int getRpp() {
        return rpp;
    }

    public void setRpp(int rpp) {
        this.rpp = rpp;
    }

    public HSSFCellStyle getStyle() {
        return style;
    }

    public void setStyle(HSSFCellStyle style) {
        this.style = style;
    }

    @SuppressWarnings("rawtypes")
    public List getResultList() {
        return resultList;
    }

    @SuppressWarnings("rawtypes")
    public void setResultList(List resultList) {
        this.resultList = resultList;
    }

}
