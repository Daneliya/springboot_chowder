package com.xxl.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.xxl.mapper.DatasourceConfigMapper;
import com.xxl.pojo.DataSourceItem;
import com.xxl.service.DatasourceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/16 0:54
 * @Version: 1.0
 */
@Component
public class DatasourceConfigServiceImpl implements DatasourceConfigService {

    @Autowired
    private DatasourceConfigMapper datasourceConfigMapper;


//    @DS("master")
    @Override
    public List<DataSourceItem> getAllDatasource() {
        return datasourceConfigMapper.getAllDatasource();
    }

    @Override
    public DataSourceItem getOneDatasource(Integer id) {
        return datasourceConfigMapper.getOneDatasource(id);
    }
}
