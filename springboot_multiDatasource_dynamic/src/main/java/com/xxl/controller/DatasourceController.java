package com.xxl.controller;

import com.xxl.pojo.DataSourceItem;
import com.xxl.service.DatasourceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/16 15:06
 * @Version: 1.0
 */
@RestController
@RequestMapping("/datasource")
public class DatasourceController {

    @Autowired
    private DatasourceConfigService datasourceConfigService;

    @GetMapping("/get")
    public List<DataSourceItem> get() {
        return datasourceConfigService.getAllDatasource();
    }

    @GetMapping("/getOne/{key}")
    public DataSourceItem getOne(String key) {
        return datasourceConfigService.getOneDatasource(key);
    }

}
