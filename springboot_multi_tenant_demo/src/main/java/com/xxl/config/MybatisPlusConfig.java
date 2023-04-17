//package com.xxl.config;
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
//import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
//import lombok.AllArgsConstructor;
//import net.sf.jsqlparser.expression.StringValue;
//import org.springframework.boot.autoconfigure.AutoConfigureBefore;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @Description: Mybatis-plus 插件,分页插件,多租户插件，动态表名插件
// * @Author: xxl
// * @Date: 2023/02/13 9:00
// * @Version: 1.0
// */
//@Configuration
//@AllArgsConstructor
//@AutoConfigureBefore(MybatisPlusConfig.class)
//@EnableConfigurationProperties(TenantProperties.class)
//public class MybatisPlusConfig {
//
//    private final TenantProperties tenantProperties;
//
//    @Bean
//    public MybatisPlusInterceptor paginationInterceptor() {
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        //动态表名插件
//        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
//        dynamicTableNameInnerInterceptor.setTableNameHandler((sql, tableName) -> {
//            String tenantId = TenantContextHolder.getTenantId();
//            //符合的表名拼接租户号
//            if (tenantProperties.getDynamicTables().stream().anyMatch(
//                    (t) -> t.equalsIgnoreCase(tableName))) {
//                return tableName + StringPool.UNDER_LINE + tenantId;
//            }
//            return tableName;
//        });
//        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);
//
//
//        // 新多租户插件配置,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存万一出现问题
//        //租户拦截器
//        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
//
//            /**
//             * 获取租户ID
//             *
//             * @return
//             */
//            @Override
//            public Expression getTenantId() {
//                String tenantId = TenantContextHolder.getTenantId();
//                if (StringUtil.isNotEmpty(tenantId)) {
//                    return new StringValue(tenantId);
//                }
//                return new NullValue();
//            }
//
//            /**
//             * 获取多租户的字段名
//             *
//             * @return String
//             */
//            @Override
//            public String getTenantIdColumn() {
//                return tenantProperties.getColumn();
//            }
//
//            /**
//             * 过滤不需要根据租户隔离的表
//             * 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
//             *
//             * @param tableName 表名
//             */
//            @Override
//            public boolean ignoreTable(String tableName) {
//                return tenantProperties.getIgnoreTables().stream().anyMatch(
//                        (t) -> tableName.startsWith(t) || tableName.equalsIgnoreCase(t)
//                );
//            }
//        }));
//        // 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
//        return interceptor;
//    }
//}