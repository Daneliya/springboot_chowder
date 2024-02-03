package com.xxl.excel.service.impl;

import com.xxl.excel.exception.BusinessException;
import com.xxl.excel.service.ExcelProgressBarService;
import com.xxl.excel.util.AsyncUtil;
import com.xxl.excel.vo.QueryVo;
import com.xxl.excel.vo.ResultVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * excel进度条测试 接口实现类
 *
 * @author xxl
 * @date 2024/2/3 22:58
 */
public class ExcelProgressBarServiceImpl implements ExcelProgressBarService {

    @Override
    public String export(QueryVo queryVo, String key) {
        //获取数据
        List<ResultVo> resultVoList = selectDatas(queryVo);
        AtomicInteger done = new AtomicInteger();
        //总数设置到redis中
        AsyncUtil.setTotal(key, resultVoList.size());
        resultVoList.forEach(vo -> {
            //数据转换 模拟耗时操作
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new BusinessException("导出异常");
            }
            done.getAndIncrement();
            AsyncUtil.setDone(key, done.get());
        });
        //组织导出数据
        Map<String, Object> map = new HashMap<>();
//        map.put("p", resultOverviewVo);
        map.put("w", resultVoList);
        return getExcelUrl(map, "propSum.xlsx", "因子分析汇总");
    }

    private List<ResultVo> selectDatas(QueryVo queryVo) {
        List<ResultVo> resultVos = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {

        }
        return resultVos;
    }

    private String getExcelUrl(Map<String, Object> map, String fileName, String name) {
        return null;
    }
}
