package com.xxl.easyexcel.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

/**
 * @Author: xxl
 * @Date: 2022/01/27 17:12
 */
@Data
@ContentRowHeight(20)
@HeadRowHeight(40)
@ColumnWidth(40)
public class ExportExcelEntity {

    @ExcelProperty(value = "用户名", index = 0)
    private String userName;

    @ColumnWidth(30)
    @ExcelProperty(value = "手机号", index = 1)
    private String mobile;

    @ColumnWidth(60)
    @ExcelProperty(value = "地址", index = 2)
    private String place;

    @ColumnWidth(60)
    @ExcelProperty(value = "身份证", index = 3)
    private String idCard;

    public ExportExcelEntity(String userName, String mobile, String place) {
        this.userName = userName;
        this.mobile = mobile;
        this.place = place;
    }

    public ExportExcelEntity(String userName, String mobile, String place, String idCard) {
        this.userName = userName;
        this.mobile = mobile;
        this.place = place;
        this.idCard = idCard;
    }
}
