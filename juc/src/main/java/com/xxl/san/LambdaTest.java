package com.xxl.san;

/**
 * @Author: xxl
 * @Date: 2022/08/11 9:34
 */
public class LambdaTest {


    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("你知道吗 我比你想象的 更想在你身边");
            }
        }).start();

        new Thread(() -> {
            System.out.println("你知道吗 我比你想象的 更想在你身边");
        }).start();
    }
}
