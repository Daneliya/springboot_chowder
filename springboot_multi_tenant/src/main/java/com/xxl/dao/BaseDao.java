package com.xxl.dao;

import com.xxl.config.DataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xxl
 * @date 2022/5/6 0:20
 */
public class BaseDao {

    private static final String TENANT_KEY = "tenant";

    @Resource
    private DataSourceBasedMultiTenantConnectionProviderImpl datasourceProvider;

    /**
     * 动态获取jdbcTemplate
     */
    protected JdbcTemplate getJdbcTemplate() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        HttpServletRequest req = attributes.getRequest();
        String tenant = req.getHeader(TENANT_KEY);
        if (StringUtils.isEmpty(tenant)) {
            return null;
        }
        return new JdbcTemplate(datasourceProvider.selectDataSource(tenant));
    }

}
