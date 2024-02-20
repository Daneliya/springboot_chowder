package com.xxl.excel.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.xxl.excel.exception.BusinessException;
import com.xxl.excel.service.ExcelProgressBarService;
import com.xxl.excel.util.AsyncUtil;
import com.xxl.excel.vo.QueryVo;
import com.xxl.excel.vo.ResultVo;
import org.springframework.stereotype.Service;

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
@Service
@SuppressWarnings("all")
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
        return getExcelUrl(map, "propSum.xlsx", "数据汇总");
    }

    /**
     * 模拟数据
     *
     * @param queryVo
     * @return
     */
    private List<ResultVo> selectDatas(QueryVo queryVo) {
        List<ResultVo> resultVos = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            ResultVo resultVo = new ResultVo();
            resultVo.setUserName("userName" + i);
            resultVo.setMobile(RandomUtil.randomNumbers(11));
            resultVos.add(resultVo);
        }
        return resultVos;
    }

    /**
     * 模拟导出 excel，返回oss地址
     *
     * @param map
     * @param fileName
     * @param name
     * @return
     */
    private String getExcelUrl(Map<String, Object> map, String fileName, String name) {
        return "www.baidu.com";
    }
}
