package com.xxl.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.xxl.easyexcel.entity.ExportExcelEntity;
import com.xxl.easyexcel.utils.FakerUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EasyexcelApplicationTests {


    @Test
    public void exportExcel() {

        //实现excel写的操作
        //1 设置写入文件夹地址和excel文件名称
        String filename = "C:\\Users\\ek\\Desktop\\write.xlsx";
        // 2 调用easyexcel里面的方法实现写操作
        // write方法两个参数：第一个参数文件路径名称，第二个参数实体类class
        EasyExcel.write(filename, ExportExcelEntity.class)
                .sheet("学生列表")
                .doWrite(getData());

//        List<ExportExcelEntity> data = getData();
//        for (ExportExcelEntity datum : data) {
//            System.out.println(datum.toString());
//        }
    }

    //创建方法返回list集合
    private static List<ExportExcelEntity> getData() {
//        List<ExportExcelEntity> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            ExportExcelEntity data = new ExportExcelEntity();
//            data.setUserName("i");
//            data.setMobile("lucy" + i);
//            data.setPlace("lucy" + i);
//            list.add(data);
//        }
        List<ExportExcelEntity> exportExcelEntities = FakerUtil.generateStudentList(500);
        return exportExcelEntities;
    }


}
