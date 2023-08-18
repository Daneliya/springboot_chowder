package com.xxl.user;

import cn.hutool.core.util.PinyinUtil;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/07/07 17:51
 * @Version: 1.0
 */
public class PinyinTest {

    @Test
    public void test01() {
//        String cn2Spell = PinyinHelper.cn2Spell("芈琒");
//        System.out.println(cn2Spell);
//        String pinyin = PinyinHelper.getAllPinyin("芈琒");
//        System.out.println(pinyin);
//
//        String feng = PinyinHelper.getAllPinyin("封锋琒");

        String[] ss = net.sourceforge.pinyin4j.PinyinHelper.toHanyuPinyinStringArray('琒');
        Arrays.stream(ss).forEach(System.out::println);
    }

    @Test
    public void hutoolPinYin() {
        String feng1 = PinyinUtil.getPinYin("锋");
        String feng2 = PinyinUtil.getPinYin("琒");
        System.out.println("feng1:" + feng1);
        System.out.println("feng2:" + feng2);
    }

    @Test
    public void tinyPinyin() throws PinyinException {
        String feng1 = PinyinHelper.getShortPinyin("琒");
        System.out.println("feng1:" + feng1);
    }

    @Test
    public void jpinyin() throws PinyinException {
        String feng1 = PinyinHelper.convertToPinyinString("琒","");
        System.out.println("feng1:" + feng1);
    }
}
