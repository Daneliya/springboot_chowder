package com.xxl.easyexcel.config;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.xxl.easyexcel.entity.ExportExcelEntity;
import com.xxl.easyexcel.model.ExcelHeader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class ExcelExportTask implements Runnable {

    private final String filePath;
    private final List<ExportExcelEntity> data;
    private final ExcelHeader header;

    public ExcelExportTask(String filePath, List<ExportExcelEntity> data, ExcelHeader header) {
        this.filePath = filePath;
        this.data = data;
        this.header = header;
    }

    @Override
    public void run() {
        try {
            OutputStream outputStream = new FileOutputStream(filePath);
//            ExcelWriter excelWriter = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX);
//            WriteWorkbook writeWorkbook = new WriteWorkbook();
//            ExcelWriter excelWriter = new ExcelWriter(writeWorkbook);
//
//            WriteSheet writeSheet = EasyExcel.writerSheet().build();
//            // 写入表头
//            List<List<String>> headList = new ArrayList<>();
//            headList.add(header.getHeaders());
//            excelWriter.write(headList, writeSheet);
//
//            // 写入数据
//            List<List<Object>> dataList = new ArrayList<>();
//            for (ExportExcelEntity excelModel : data) {
//                List<Object> rowData = new ArrayList<>();
//                rowData.add(excelModel.getMobile());
//                rowData.add(excelModel.getUserName());
//                dataList.add(rowData);
//            }
//
//            excelWriter.write(dataList, writeSheet);
//            excelWriter.finish();
//            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}