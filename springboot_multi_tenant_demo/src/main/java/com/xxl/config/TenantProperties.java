//package com.xxl.config;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @Description: 租户配置属性(可以写在yml配置文件里, 也可以在这儿写死)
// * @Author: xxl
// * @Date: 2023/02/13 8:57
// * @Version: 1.0
// */
//@Getter
//@Setter
//@RefreshScope
//@ConfigurationProperties(prefix = "tenant")
//public class TenantProperties {
//
//    /**
//     * 是否开启租户模式
//     */
//    private Boolean enable = true;
//
//    /**
//     * 需要排除的多租户的表(根据自己需要修改)
//     * /
//     * private List ignoreTables = Arrays.asList(“t_menu”, “t_oauth_client”,“t_tenant_info”,“t_server_info”);
//     * /*
//     * 动态表名的表(根据自己需要修改)
//     */
//    private List dynamicTables = Arrays.asList("t_opt_log", "t_flow");
//
//    /**
//     * 多租户字段名称(根据实际项目修改)
//     */
//    private String column = "tenant_id";
//
//    /**
//     * 排除不进行租户隔离的sql
//     * 样例全路径：com.cherf.system.sauth.mapper.AppAccessMapper.findList
//     */
//    private List ignoreSqls = new ArrayList<>();
//}