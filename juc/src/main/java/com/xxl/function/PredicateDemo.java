package com.xxl.function;

import java.util.function.Predicate;

/**
 * 断定型接口：有一个输入参数，返回值只能是 布尔值！
 *
 * @author xxl
 * @date 2022/7/23 22:06
 */
public class PredicateDemo {

    public static void main(String[] args) {
        // 判断字符串是否为空
//        Predicate<String> predicate = new Predicate<String>(){
////            @Override
////            public boolean test(String str) {
////                return str.isEmpty();
////            }
////        };

        Predicate<String> predicate = (str) -> {
            return str.isEmpty();
        };

        System.out.println(predicate.test(""));

    }

}
