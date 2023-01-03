package com.xxl.itlaoge.handler;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 工厂设计模式
 *
 * @author xxl
 * @date 2023/1/3 23:42
 */
public class Factory {

    private static Map<String, Handler> strategyMap = Maps.newHashMap();

    public static Handler getInvokeStrategy(String str) {
        return strategyMap.get(str);
    }

    public static void register(String name, Handler handler) {
        if (StringUtils.isEmpty(name) || handler == null) {
            return;
        }
        strategyMap.put(name, handler);
    }
}
