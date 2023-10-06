//package com.xxl.file;
//
///**
// * @Description: 资源映射路径 为springboot访问静态资源做准备
// * @Author: xxl
// * @Date: 2023/05/22 16:38
// * @Version: 1.0
// */
//@Configuration
//public class MyWebMvcConfigurer implements WebMvcConfigurer {
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //  /images/** 映射到哪里去,前端里面的路由      file:/home/fileUpload/ 表示需要访问linux系统文件所在的文件夹路径名称
//        registry.addResourceHandler("/images/**").addResourceLocations("file:/usr/local/file/data/");
//    }
//
//}
