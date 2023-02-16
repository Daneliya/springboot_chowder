package com.xxl.service;

import com.xxl.pojo.DataSourceItem;

import java.util.List;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/16 0:53
 * @Version: 1.0
 */
public interface DatasourceConfigService {

    List<DataSourceItem> getAllDatasource();

    DataSourceItem getOneDatasource(Integer id);
}
