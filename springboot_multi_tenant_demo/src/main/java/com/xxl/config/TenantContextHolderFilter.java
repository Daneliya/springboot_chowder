//package com.xxl.config;
//
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.GenericFilterBean;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @Description: 租户上下文过滤器
// * @Author: xxl
// * @Date: 2023/02/13 8:51
// * @Version: 1.0
// */
//@Slf4j
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class TenantContextHolderFilter extends GenericFilterBean {
//
//    @Override
//    @SneakyThrows
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        try {
//            String tenantId = request.getHeader(Constant.TENANT_ID);
//            if (StringUtils.isBlank(tenantId)) {
//                tenantId = Constant.TENANT_ID_DEFAULT;
//            }
//            log.info("获取到的租户ID为:{} ", tenantId);
//            if (StringUtils.isNotBlank(tenantId)) {
//                TenantContextHolder.setTenantId(tenantId);
//            } else {
//                if (StringUtils.isBlank(TenantContextHolder.getTenantId())) {
//                    TenantContextHolder.setTenantId(Constant.TENANT_ID_DEFAULT);
//                }
//            }
//            filterChain.doFilter(request, response);
//        } finally {
//            TenantContextHolder.clear();
//        }
//    }
//}