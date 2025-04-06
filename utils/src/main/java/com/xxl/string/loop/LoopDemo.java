package com.xxl.string.loop;

/**
 * @Classname LoopDemo
 * @Description TODO
 * @Date 2025/4/7 00:14
 * @Created by xxl
 */
public class LoopDemo {

    public static void main(String[] args) {
        int size = 500;
        System.out.println("开始运行");
        for (int i = 0; i < size; i++) {
            doSth();
            System.out.print("进度：" + (i + 1) + "/" + size + "\r");
        }
        System.out.println();
        System.out.println("结束运行");
    }

    private static void doSth() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
