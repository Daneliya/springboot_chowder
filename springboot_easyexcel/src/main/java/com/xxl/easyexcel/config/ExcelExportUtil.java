package com.xxl.easyexcel.config;

import com.google.common.collect.Lists;
import com.xxl.easyexcel.entity.ExportExcelEntity;
import com.xxl.easyexcel.model.ExcelHeader;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xxl
 * @Date: 2024/5/16 下午2:42
 */
public class ExcelExportUtil {

    private static final int THREAD_COUNT = 5;

    public static void exportExcel(String filePath, List<ExportExcelEntity> data, ExcelHeader header)
            throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        List<List<ExportExcelEntity>> dataList = Lists.partition(data, data.size() / THREAD_COUNT);
        for (List<ExportExcelEntity> subList : dataList) {
            executorService.execute(new ExcelExportTask(filePath, subList, header));
        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

}
