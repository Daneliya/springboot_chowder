package com.xxl.demo;

import com.google.common.cache.*;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author xxl
 * @date 2022/8/27 0:39
 */
@SpringBootTest
public class CacheTest {

    @Test
    public void testCache() throws ExecutionException, InterruptedException {

        CacheLoader cacheLoader = new CacheLoader<String, Animal>() {
            // 如果找不到元素，会调用这里
            @Override
            public Animal load(String s) {
                return null;
            }
        };
        LoadingCache<String, Animal> loadingCache = CacheBuilder.newBuilder()
                .maximumSize(1000) // 容量
                .expireAfterWrite(3, TimeUnit.SECONDS) // 过期时间
                .removalListener(new MyRemovalListener()) // 失效监听器
                .build(cacheLoader); //
        loadingCache.put("狗", new Animal("旺财", 1));
        loadingCache.put("猫", new Animal("汤姆", 3));
        loadingCache.put("狼", new Animal("灰太狼", 4));

        loadingCache.invalidate("猫"); // 手动失效

        Animal animal = loadingCache.get("狼");
        System.out.println("【获取信息】" + animal.toString());
        Thread.sleep(4 * 1000);
        // 狼已经自动过去，获取为 null 值报错
        //System.out.println(loadingCache.get("狼"));
    }

    /**
     * 缓存移除监听器
     */
    class MyRemovalListener implements RemovalListener<String, Animal> {

        @Override
        public void onRemoval(RemovalNotification<String, Animal> notification) {
            String reason = String.format("key=%s,value=%s,reason=%s", notification.getKey(), notification.getValue(), notification.getCause());
            System.out.println(reason);
        }
    }

    static class Animal {
        private String name;
        private Integer age;

        public Animal(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Animal{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
