package com.xxl.config.dao;

import com.xxl.config.vo.DataSourceConfig;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xxl
 * @date 2022/5/5 23:36
 */
@Repository
public class DataSourceConfigDao {

    @Resource
    private JdbcTemplate jdbcTemplate;
    //https://blog.csdn.net/qq_40643699/article/details/115582536
    /**
     * 查询所有租户
     *
     * @return
     */
    public List<DataSourceConfig> getAll() {
        String sql = "select * from x_datasource_config";
        return this.jdbcTemplate.query(sql, resultMap());
    }

    /**
     * 根据租户名称查询租户配置
     *
     * @param name
     * @return
     */
    public DataSourceConfig getByName(String name) {
        String sql = "select * from x_datasource_config where _name = ?";
        List<DataSourceConfig> list = this.jdbcTemplate.query(sql, resultMap(), name);
        return list.size() == 0 ? null : list.get(0);
    }

    private RowMapper<DataSourceConfig> resultMap() {
        return (rs, rowNum) -> {
            DataSourceConfig dataSourceConfig = new DataSourceConfig();
            dataSourceConfig.setId(rs.getLong("id"));
            dataSourceConfig.setName(rs.getString("name"));
            dataSourceConfig.setPassword(rs.getString("password"));
            dataSourceConfig.setUsername(rs.getString("username"));
            dataSourceConfig.setUrl(rs.getString("url"));
            dataSourceConfig.setDriverClassName(rs.getString("driver_class_name"));
            return dataSourceConfig;
        };
    }

}
