package com.xxl.meituan.completableFuture;

import com.google.common.util.concurrent.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xxl
 * @date 2023/1/2 22:24
 */
public class FutureTest {


    public static void main(String[] args) {
        // 创建一个线程缓冲池Service
        ExecutorService executor = Executors.newFixedThreadPool(5);
        // 创建一个ListeningExecutorService实例
        ListeningExecutorService guavaExecutor = MoreExecutors.listeningDecorator(executor);
        // 提交一个可监听的线程(可以返回自定义对象，也可直接返回String)
        ListenableFuture<String> future1 = guavaExecutor.submit(() -> {
            //step 1
            System.out.println("执行step 1");
            return "step1 result";
        });
        ListenableFuture<String> future2 = guavaExecutor.submit(() -> {
            //step 2
            System.out.println("执行step 2");
            return "step2 result";
        });
        ListenableFuture<List<String>> future1And2 = Futures.allAsList(future1, future2);

        // 线程结果处理回调函数
        Futures.addCallback(future1And2, new FutureCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                System.out.println(result);
                ListenableFuture<String> future3 = guavaExecutor.submit(() -> {
                    System.out.println("执行step 3");
                    return "step3 result";
                });
                Futures.addCallback(future3, new FutureCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                    }
                }, guavaExecutor);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        }, guavaExecutor);
    }


}
