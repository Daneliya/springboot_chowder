package com.xxl.itlaoge.handler2;

import com.google.common.collect.Maps;
import com.xxl.itlaoge.handler.Handler;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 工厂设计模式
 *
 * @author xxl
 * @date 2023/1/3 23:42
 */
public class Factory2 {

    private static Map<String, AbstractHandler> strategyMap = Maps.newHashMap();

    public static AbstractHandler getInvokeStrategy(String str) {
        return strategyMap.get(str);
    }

    public static void register(String name, AbstractHandler handler) {
        if (StringUtils.isEmpty(name) || handler == null) {
            return;
        }
        strategyMap.put(name, handler);
    }
}
