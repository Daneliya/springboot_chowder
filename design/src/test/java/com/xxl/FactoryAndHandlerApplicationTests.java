package com.xxl;

import com.xxl.itlaoge.handler.*;

import com.xxl.itlaoge.handler2.AbstractHandler;
import com.xxl.itlaoge.handler2.Factory2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @link：https://www.bilibili.com/video/BV1b5411a7oa
 *
 * @author xxl
 * @date 2023/1/3 23:48
 */
@SpringBootTest
public class FactoryAndHandlerApplicationTests {

    @Test
    public void noDesign() {
        String name = "张三";
        //=========
        if (name.equals("张三")) {
            System.out.println("张三完成任务");
        } else if (name.equals("李四")) {
            System.out.println("李四完成任务");
        } else if (name.equals("王五")) {
            System.out.println("王五完成任务");
        }
        //==========
        if (name.equals("张三")) {
            new ZhangSanHandler().AAA(name);
        } else if (name.equals("李四")) {
            new LiSiHandler().AAA(name);
        } else if (name.equals("王五")) {
            new WangWuHandler().AAA(name);
        }
    }

    @Test
    public void design() {
        String name = "张三";
        Handler invokeStrategy = Factory.getInvokeStrategy(name);
        invokeStrategy.AAA(name);
    }

    // 工厂+策略+模板设计模式
    @Test
    public void design2() {
        String name = "李四";
        AbstractHandler strategy = Factory2.getInvokeStrategy(name);
        strategy.AAA(name);

//        String name2 = "张三";
//        AbstractHandler strategy2 = Factory2.getInvokeStrategy(name2);
//        System.out.println(strategy2.BBB(name2));
//        String str = strategy.BBB(name2);
//        System.out.println(str);
    }
}
