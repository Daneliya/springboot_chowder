package com.xxl.kuangshen.function;

import java.util.function.Function;

/**
 * Function 函数型接口, 有一个输入参数，有一个输出
 * 只要是 函数型接口 可以 用 lambda表达式简化
 *
 * @author xxl
 * @date 2022/7/23 22:04
 */
public class FunctionDemo {

    public static void main(String[] args) {
        //
//        Function<String,String> function = new Function<String,String>() {
//            @Override
//            public String apply(String str) {
//                return str;
//            }
//        };

        Function<String, String> function = str -> {
            return str;
        };

        System.out.println(function.apply("asd"));
    }


}
