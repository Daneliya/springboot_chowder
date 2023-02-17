package com.xxl.mapper;

import com.xxl.pojo.DataSourceItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/16 0:54
 * @Version: 1.0
 */
@Mapper
public interface DatasourceConfigMapper {

    List<DataSourceItem> getAllDatasource();

    DataSourceItem getOneDatasource(String key);
}
