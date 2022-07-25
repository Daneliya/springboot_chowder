package com.xxl.kuangshen.function;

import java.util.function.Supplier;

/**
 * Supplier 供给型接口 没有参数，只有返回值
 *
 * @author xxl
 * @date 2022/7/24 0:05
 */
public class SupplierDemo {

    public static void main(String[] args) {
//        Supplier supplier = new Supplier<Integer>() {
//            @Override
//            public Integer get() {
//                System.out.println("get()");
//                return 1024;
//            }
//        };

        Supplier supplier = () -> {
            return 1024;
        };
        System.out.println(supplier.get());
    }

}
