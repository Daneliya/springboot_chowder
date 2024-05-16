package com.xxl.easyexcel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.github.javafaker.Faker;
import com.xxl.easyexcel.constant.ExcelConstants;
import com.xxl.easyexcel.entity.ExportExcelEntity;
import com.xxl.easyexcel.service.ExportService;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xxl
 * @date 2024/5/17 0:20
 */
@Service
public class ExportServiceImpl implements ExportService {


    @Override
    public void export() throws IOException {
        OutputStream outputStream = null;
        try {
            //记录总数:实际中需要根据查询条件进行统计即可
            Integer totalCount = 100000;
            //每一个Sheet存放100w条数据
            Integer sheetDataRows = ExcelConstants.PER_SHEET_ROW_COUNT;
            //每次写入的数据量20w,每页查询20W
            Integer writeDataRows = ExcelConstants.PER_WRITE_ROW_COUNT;
            //计算需要的Sheet数量
            int sheetNum = totalCount % sheetDataRows == 0 ? (totalCount / sheetDataRows) : (totalCount / sheetDataRows + 1);
            //计算一般情况下每一个Sheet需要写入的次数(一般情况不包含最后一个sheet,因为最后一个sheet不确定会写入多少条数据)
            int oneSheetWriteCount = sheetDataRows / writeDataRows;
            //计算最后一个sheet需要写入的次数
            int lastSheetWriteCount = totalCount % sheetDataRows == 0 ? oneSheetWriteCount : (totalCount % sheetDataRows % writeDataRows == 0 ? (totalCount / sheetDataRows / writeDataRows) : (totalCount / sheetDataRows / writeDataRows + 1));

            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = requestAttributes.getResponse();
            outputStream = response.getOutputStream();
            //必须放到循环外，否则会刷新流
            ExcelWriter excelWriter = EasyExcel.write(outputStream).build();

            //开始分批查询分次写入
            for (int i = 0; i < sheetNum; i++) {
                //创建Sheet
                WriteSheet sheet = new WriteSheet();
                sheet.setSheetName("测试Sheet1" + i);
                sheet.setSheetNo(i);
                //循环写入次数: j的自增条件是当不是最后一个Sheet的时候写入次数为正常的每个Sheet写入的次数,如果是最后一个就需要使用计算的次数lastSheetWriteCount
                for (int j = 0; j < (i != sheetNum - 1 ? oneSheetWriteCount : lastSheetWriteCount); j++) {
                    //分页查询一次20w
                    List<ExportExcelEntity> exportExcelEntities = listStudentList(1000);
                    WriteSheet writeSheet = EasyExcel.writerSheet(i, "员工信息" + (i + 1))
                            .head(ExportExcelEntity.class)
                            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build();
                    //写数据
                    excelWriter.write(exportExcelEntities, writeSheet);
                }
            }
            // 下载EXCEL
            response.setContentType("application/vnd.openxmlfORMats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止浏览器端导出excel文件名中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("员工信息", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            excelWriter.finish();
            outputStream.flush();
        } catch (IOException | BeansException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
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
