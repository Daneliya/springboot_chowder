package com.xxl.common.mail.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * 邮件工厂
 *
 * @Author: xxl
 * @Date: 2024/11/27 10:30
 */
public class SendMailFactory {

    private static final Map<String, SendMailHandler> strategyMap = new HashMap<>();

    public static SendMailHandler getInvokeStrategy(String str) {
        return strategyMap.get(str);
    }

    public static void register(String str, SendMailHandler handler) {
        if (str == null || str.isEmpty() || handler == null) {
            return;
        }
        strategyMap.put(str, handler);
    }

}
