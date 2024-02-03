package com.xxl.excel.controller;

import com.xxl.excel.exception.BusinessException;
import com.xxl.excel.result.RestBuilders;
import com.xxl.excel.result.RestMessage;
import com.xxl.excel.service.ExcelProgressBarService;
import com.xxl.excel.util.AsyncUtil;
import com.xxl.excel.vo.QueryVo;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * excel进度条测试 控制器
 *
 * @author xxl
 * @date 2024/2/3 22:24
 */
@RestController
public class ExcelProgressBarController {

    private ExcelProgressBarService excelProgressBarService;

    /**
     * 导出
     *
     * @param queryVo
     * @return 统一出参
     */
    @PostMapping("/v1/export")
    public RestMessage export(@RequestBody QueryVo queryVo) {
        Assert.notNull(queryVo, "查询参数不能为空");
        Assert.notNull(queryVo.getStartTime(), "开始时间不能为空");
        Assert.notNull(queryVo.getEndTime(), "结束时间不能为空");
        Assert.isTrue(StringUtils.isNotBlank(queryVo.getQueryType()), "查询类型不能为空");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String key = "data:" + formatter.format(new Date());
        AsyncUtil.submitTask(key, () -> {
            //获取并组织excel数据
            String url;
            try {
                url = excelProgressBarService.export(queryVo, key);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
            return url;
        });
        return RestBuilders.successBuilder().data(key).build();
    }

    /**
     * 根据key获取导出接口
     *
     * @param key
     * @return
     */
    @GetMapping("v1/getProgress/{key}")
    public RestMessage getProgress(@PathVariable String key) {
        Assert.hasLength(key, "key不能为空");
        return RestBuilders.successBuilder().data(AsyncUtil.getResult(key)).build();
    }


}
