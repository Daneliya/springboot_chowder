package com.xxl.demo;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author xxl
 * @date 2022/8/27 0:11
 */
@SpringBootTest
public class CollectTest {

//    public static void main(String[] args) {
//        test01();
//    }

    /**
     * 不可变集合
     */
    @Test
    public void immutableListTest() {
        List<String> list = new ArrayList<>();
        list.add("雷军");
        list.add("乔布斯");

        List<String> unmodifiableList = Collections.unmodifiableList(list);
        //unmodifiableList.add("马云"); // 抛出 UnsupportedOperationException 异常
        //list.add("马云"); // unmodifiableList 的元素也是跟着发生变化


        List<String> stringArrayList = Lists.newArrayList("雷军", "乔布斯");
        ImmutableList<String> immutableList = ImmutableList.copyOf(stringArrayList);
        //immutableList.add("马云"); // 抛出 UnsupportedOperationException。提示 add() 方法废弃了
    }

    /**
     * 字符串处理——连接器
     */
    @Test
    public void joinerTest() {
        // skipNulls() 方法直接忽略 null
        Joiner joiner = Joiner.on("; ").skipNulls();
        String join = joiner.join("雷军", null, "乔布斯");
        System.out.println(join);
        // useForNull(String) 方法用某个字符串来替换 null
        Joiner joiner2 = Joiner.on("; ").useForNull("暂无数据");
        String join2 = joiner2.join("雷军", null, "乔布斯");
        System.out.println(join2);
    }


}
