package com.xxl.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.github.javafaker.Faker;
import com.xxl.easyexcel.entity.ExportExcelEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        List<ExportExcelEntity> exportExcelEntities = listStudentList(500);
        return exportExcelEntities;
    }

    /**
     * faker 指定汉语，默认英语
     */
    private static Faker FAKER = new Faker(Locale.CHINA);

    /**
     * 随机生成一定数量学生
     *
     * @param number 数量
     * @return 学生
     */
    public static List<ExportExcelEntity> listStudentList(final int number) {
        return Stream.generate(() -> new ExportExcelEntity(
                        FAKER.name().fullName(),
                        FAKER.phoneNumber().cellPhone(),
                        FAKER.address().city() + FAKER.address().streetAddress(),
                        FAKER.idNumber().validSvSeSsn()))
                .limit(number)
                .collect(Collectors.toList());
    }

}
