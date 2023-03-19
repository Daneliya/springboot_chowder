package com.xxl.spring;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;

/**
 * 自定义spring容器
 *
 * @author xxl
 * @date 2023/3/19 15:38
 */
public class ApplicationContext {

    private Class configClass;

    public ApplicationContext(Class configClass) {
        this.configClass = configClass;
        // 扫描，判断类上有没有注解
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan configClassAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            String path = configClassAnnotation.value(); // 扫描路径 com.xxl.service
            path = path.replace(".", "/"); // com/xxl/service
            // 获取类加载器
            ClassLoader classLoader = ApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path);
            File file = new File(resource.getFile());

            //System.out.println(file);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    String fileName = f.getAbsolutePath();
                    //System.out.println(fileName);
                    // 是不是以class结尾
                    if (fileName.endsWith(".class")) {
                        String className = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".class"));
                        className = className.replace("\\", ".");
                        System.out.println(className);
                        try {
                            Class<?> clazz = classLoader.loadClass(className);
                            if (clazz.isAnnotationPresent(ComponentScan.class)) {
                                // Bean

                            }
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

        }
    }

    public Object getBean(String beanName) {
        return null;
    }
}
