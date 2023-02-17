package com.xxl.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.xxl.mapper.DatasourceConfigMapper;
import com.xxl.pojo.DataSourceItem;
import com.xxl.service.DatasourceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/16 0:54
 * @Version: 1.0
 */
@Service("DatasourceConfigService")
public class DatasourceConfigServiceImpl implements DatasourceConfigService {

    @Autowired
    private DatasourceConfigMapper datasourceConfigMapper;


    //    @DS("master")
    @Override
    public List<DataSourceItem> getAllDatasource() {
        System.out.println("执行查询所有数据源 - getAllDatasource()方法");
        return datasourceConfigMapper.getAllDatasource();
    }

    //    @DS("master")
    @Override
    public DataSourceItem getOneDatasource(String key) {
        System.out.println("执行查询单个数据源 - getOneDatasource()方法 || 参数 - " + key);
        DataSourceItem oneDatasource = datasourceConfigMapper.getOneDatasource(key);
        System.out.println("执行查询单个数据源 - getOneDatasource()方法 || 结果 " + oneDatasource);
        return oneDatasource;
    }
}
