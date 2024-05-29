package com.xxl.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.xxl.easyexcel.entity.ExportExcelEntity;
import com.xxl.easyexcel.utils.FakerUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class EasyExcelTests {


    @Test
    public void exportExcel() {
        long startTimeMillis = System.currentTimeMillis();
        // 实现excel写的操作
        // 1 设置写入文件夹地址和excel文件名称
        String filename = "C:\\Users\\ek\\Desktop\\write.xlsx";
        // 2 调用easyexcel里面的方法实现写操作
        // write方法两个参数：第一个参数文件路径名称，第二个参数实体类class
        EasyExcel.write(filename, ExportExcelEntity.class)
                .sheet("学生列表")
                .doWrite(getData());

        long endTimeMillis = System.currentTimeMillis();
        long totalMilliseconds = endTimeMillis - startTimeMillis;
        double totalSeconds = totalMilliseconds / 1000.0;  // 使用1000.0确保结果是double类型，并保留小数部分（如果有的话）
        System.out.println("耗时：" + totalSeconds + " 秒");
    }

    /**
     * 创建方法返回list集合
     *
     * @return
     */
    private static List<ExportExcelEntity> getData() {
        return FakerUtil.generateStudentList(16000);
    }

    /**
     * 打印数据
     */
    private void printList() {
        List<ExportExcelEntity> data = getData();
        for (ExportExcelEntity datum : data) {
            System.out.println(datum.toString());
        }
    }


}
