package com.xxl.easyexcel;

import com.xxl.easyexcel.entity.ExportExcelEntity;
import com.xxl.easyexcel.utils.FakerUtil;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;


/**
 * csv 导出测试
 *
 * @Author: xxl
 * @Date: 2024/5/29 下午3:15
 */
@SpringBootTest
public class CSVTests {

    private final ExecutorService threadPool = Executors.newFixedThreadPool(10); // 可以根据实际情况调整线程池的大小

    @Test
    public void exportExcel() {
        long startTimeMillis = System.currentTimeMillis();
        // 实现excel写的操作
        // 1 设置写入文件夹地址和excel文件名称
        String filename = "C:\\Users\\ek\\Desktop\\writeCSV.csv";
        // 2 调用easyexcel里面的方法实现写操作
        // write方法两个参数：第一个参数文件路径名称，第二个参数实体类class
        List<ExportExcelEntity> dataList = getData();
//        List<String[]> dataArr = dataList.stream().map(e -> {
//            String[] data = new String[4];
//            data[0] = e.getUserName();
//            data[1] = e.getMobile();
//            data[2] = e.getIdCard();
//            data[3] = e.getPlace();
//            return data;
//        }).collect(Collectors.toList());
//        CsvWriter writer = CsvUtil.getWriter(filename, CharsetUtil.CHARSET_UTF_8);
//        writer.write(dataArr);

//        conversionXSSF("writeCSV", dataArr);
//        conversionSXSSF("writeCSV", dataArr);
        exportExcel(dataList);

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
     * 转换Excel（XSSF）
     *
     * @param csvFileName
     * @param csvDataList
     */
    @SneakyThrows
    private static void conversionXSSF(String csvFileName, List<String[]> csvDataList) {
        // 创建一个 xlsx 工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();

        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
        String outputExcelFileName = desktopPath + File.separator + csvFileName + ".xlsx";
        FileOutputStream out = new FileOutputStream(new File(outputExcelFileName));

        // 创建工作表
        XSSFSheet spreadsheet = workbook.createSheet("Sheet1");

        // 将 csv 数据存到 xlsx 文件中
        for (int rowNum = 0; rowNum < csvDataList.size(); rowNum++) {
            // 获取一行的数据
            String[] csvFileRowData = csvDataList.get(rowNum);
            XSSFRow row = spreadsheet.createRow(rowNum);
            for (int columnNum = 0; columnNum < csvFileRowData.length; columnNum++) {
                XSSFCell cell = row.createCell(columnNum);
                cell.setCellValue(csvFileRowData[columnNum]);
            }
        }
        workbook.write(out);
        out.close();
    }

    /**
     * 转换Excel（SXSSF）
     *
     * @param csvFileName
     * @param csvDataList
     */
    @SneakyThrows
    private static void conversionSXSSF(String csvFileName, List<String[]> csvDataList) {
        // 创建一个 xlsx 工作簿
        SXSSFWorkbook workbook = new SXSSFWorkbook();

        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
        String outputExcelFileName = desktopPath + File.separator + csvFileName + ".xlsx";
        FileOutputStream out = new FileOutputStream(new File(outputExcelFileName));

        // 创建工作表
        SXSSFSheet spreadsheet = workbook.createSheet("Sheet1");

        // 将 csv 数据存到 xlsx 文件中
        for (int rowNum = 0; rowNum < csvDataList.size(); rowNum++) {
            // 获取一行的数据
            String[] csvFileRowData = csvDataList.get(rowNum);
            SXSSFRow row = spreadsheet.createRow(rowNum);
            for (int columnNum = 0; columnNum < csvFileRowData.length; columnNum++) {
                SXSSFCell cell = row.createCell(columnNum);
                cell.setCellValue(csvFileRowData[columnNum]);
            }
        }
        workbook.write(out);
        out.close();
    }

    public void exportExcel(List<ExportExcelEntity> dataList) {
        //表头数据，根据自己实际业务
//        List<Map<String, Object>> tableTitleInfoList;

        //查询需要导出的总数
        long count = getData().size();
        //2007版Excel最大行数是1048576,表头占用一行，数据最大占用1048575,超过后进行分sheet
        //sheet页数
        long sheet = (count + 1048574) / 1048575;
        // 创建工作簿
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        List<SXSSFSheet> sheets = new ArrayList<>();
        for (int i = 1; i <= sheet; i++) {
            // 创建数据 sheet
            SXSSFSheet dataSheet = workbook.createSheet("数据" + i);
            //将第一列隐藏，根据自己的业务可不加，我这里导出的时候把表的id也导出了，但不给看给隐藏了
            dataSheet.setColumnHidden(0, true);
            // 创建表头行
            SXSSFRow headerRow = dataSheet.createRow(0);
            //设置样式
            CellStyle blackStyle = workbook.createCellStyle();
            //自动换行
            blackStyle.setWrapText(true);
            //存储最大列宽
            Map<Integer, Integer> maxWidth = new HashMap<>();
            // 新增第一列id列
            SXSSFCell idDataCell = headerRow.createCell(0);
            idDataCell.setCellValue("id");
            int columnIndex = 1;
//            for (Map<String, Object> tableTitle : tableTitleInfoList) {
//                String columnName = tableTitle.get("tableTitle").toString();
//                SXSSFCell cell = headerRow.createCell(columnIndex);
//                cell.setCellValue(columnName);
//                maxWidth.put(columnIndex, columnName.getBytes().length * 256 + 200);
//                cell.setCellStyle(blackStyle);//设置自动换行
//                columnIndex++;
//            }
            for (ExportExcelEntity exportExcelEntity : dataList) {
                String columnName = "idCard";
                SXSSFCell cell = headerRow.createCell(columnIndex);
                cell.setCellValue(columnName);
                maxWidth.put(columnIndex, columnName.getBytes().length * 256 + 200);
                cell.setCellStyle(blackStyle);//设置自动换行
                columnIndex++;
            }
            sheets.add(dataSheet);
        }
        int size = 500;
        //循环次数
        long cycles = count / size;
        List<FutureTask<List<Map<String, Object>>>> resultList = new ArrayList<>();
        for (int i = 0; i <= cycles + 1; i++) {
            long offset = (long) i * size;
            //具体的查询任务，也就是你实际的数据查询
//            FutureTask<List<Map<String, Object>>> futureTask = new FutureTask<>(() -> exportExcelMapper.getData(offset, size));
            // 修改后的代码
            int start = (int) offset;
            int end = (int) Math.min(offset + size, dataList.size());
            FutureTask<List<Map<String, Object>>> futureTask = new FutureTask<>(() -> {
                List<ExportExcelEntity> subList = dataList.subList(start, end);
                List<Map<String, Object>> mapList = new ArrayList<>();
                for (ExportExcelEntity entity : subList) {
                    Map<String, Object> map = new HashMap<>();
                    // 假设ExportExcelEntity有getId和其他getter方法
                    map.put("idCard", entity.getIdCard());
                    map.put("mobile", entity.getMobile());
                    map.put("userName", entity.getUserName());
                    map.put("place", entity.getPlace());
                    // 添加其他需要的字段
                    mapList.add(map);
                }
                return mapList;
            });
            //把任务丢给线程池调度执行
            threadPool.execute(futureTask);
            //future异步模式，把任务放进去，先不取结果
            resultList.add(futureTask);
        }
        // 按行填充数据
        int rowIndex = 1;
        //已写进excel的行数
        int totalExcelCount = 1;
        while (!resultList.isEmpty()) {
            Iterator<FutureTask<List<Map<String, Object>>>> iterator = resultList.iterator();
            while (iterator.hasNext()) {
                try {
                    //得到数据
                    List<Map<String, Object>> allDataList = iterator.next().get();
                    //获取一个就删除一个任务
                    iterator.remove();
                    for (Map<String, Object> data : allDataList) {
                        //找到数据该放到第几个sheet页
                        long oneSheet = (totalExcelCount + 1048574) / 1048575;
                        SXSSFSheet dataSheet = sheets.get((int) (oneSheet - 1));
                        //每次换新的sheet页行号需要重新算
                        if (rowIndex % 1048576 == 0) {
                            rowIndex = 1;
                        }
                        SXSSFRow dataRow = dataSheet.createRow(rowIndex++);
                        totalExcelCount++;
                        int columnIndex = 1;
                        // 首先添加该行的第一列id数据
                        String id = data.get("idCard").toString();
                        SXSSFCell idTitleCell = dataRow.createCell(0);
                        idTitleCell.setCellValue(id);
                        // 再循环遍历其余列，添加数据
//                        for (Map<String, Object> tableTitle : tableTitleInfoList) {
//                            //表头和数据是通过code字段对应的
//                            String titleName = tableTitle.get("code").toString().toLowerCase();
//                            Object value = data.get(titleName);
//                            SXSSFCell cell = dataRow.createCell(columnIndex);
//                            if (value != null) {
//                                cell.setCellValue(value.toString());
//                            }
//                            columnIndex++;
//                        }
                        for (ExportExcelEntity exportExcelEntity : dataList) {
                            //表头和数据是通过code字段对应的
                            String titleName = "userName";
                            Object value = data.get(titleName);
                            SXSSFCell cell = dataRow.createCell(columnIndex);
                            if (value != null) {
                                cell.setCellValue(value.toString());
                            }
                            columnIndex++;
                        }
                    }
                } catch (InterruptedException | ExecutionException e) {
//                    log.error("多线程查询出现异常：{}", e.getMessage());
                }
            }
        }

        // 导出Excel
        String fileName = "data";
        try {
//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition", "attachment; filename="
//                    + URLEncoder.encode(fileName + ".xls", StandardCharsets.UTF_8));
//            OutputStream out = response.getOutputStream();

            String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
            String outputExcelFileName = desktopPath + File.separator + fileName + ".xlsx";
            FileOutputStream out = new FileOutputStream(new File(outputExcelFileName));
            workbook.write(out);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
