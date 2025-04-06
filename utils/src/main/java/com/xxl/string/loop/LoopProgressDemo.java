package com.xxl.string.loop;

/**
 * @Classname LoopProgressDemo
 * @Description TODO
 * @Date 2025/4/7 00:15
 * @Created by xxl
 */
public class LoopProgressDemo {
    public static void main(String[] args) {
        int size = 500;
        ProgressBar progressBar = new ProgressBar(size, 60, 3);

        System.out.println("开始运行");
        for (int i = 0; i < size; i++) {
            doSth();
            progressBar.update(i + 1);
        }
    }

    private static void doSth() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
