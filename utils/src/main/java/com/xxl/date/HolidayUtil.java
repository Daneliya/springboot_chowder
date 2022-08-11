package com.xxl.date;

import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: xxl
 * @Date: 2022/08/03 14:26
 */
public class HolidayUtil {

    public static void main(String[] args) {
        System.out.println(getJjr(2021, 4));
        Map<String, Object> jjr = getJjr(2021, 4);
        Object holiday = jjr.get("holiday");
        String holidayStr = JSONObject.toJSONString(holiday);

        // 组装节假日（name，date）
        List<Map<String, String>> resultList = new ArrayList<>();
        Map<String, Object> holidayMap = JSONObject.parseObject(holidayStr, Map.class);
        for (String date : holidayMap.keySet()) {
            String holidayDate = holidayMap.get(date).toString();
            JSONObject jsonObject = JSONObject.parseObject(holidayDate);

            Map<String, String> map = new HashMap<>();
            map.put("name", jsonObject.getString("name"));
            map.put("date", jsonObject.getString("date"));
            resultList.add(map);
        }
//        resultList.stream().forEach(System.out::println);

        // 补班
        List<Map<String, String>> makeList = resultList.stream().filter(e -> e.get("name").contains("补班")).collect(Collectors.toList());
        makeList.stream().forEach(System.out::println);


//        System.out.println(holidayDate);
//        System.out.println(getMonthWekDay(2021, 4));
//        System.out.println(JJR(2021, 4));

    }

    /**
     * 获取周末和节假日
     *
     * @param year
     * @param month
     * @return
     */
    public static Set<String> JJR(int year, int month) {
        //获取所有的周末
        Set<String> monthWekDay = getMonthWekDay(year, month);
        //http://timor.tech/api/holiday api文档地址
        Map jjr = getJjr(year, month + 1);
        Integer code = (Integer) jjr.get("code");
        if (code != 0) {
            return monthWekDay;
        }
        Map<String, Map<String, Object>> holiday = (Map<String, Map<String, Object>>) jjr.get("holiday");
        Set<String> strings = holiday.keySet();
        for (String str : strings) {
            Map<String, Object> stringObjectMap = holiday.get(str);
            Integer wage = (Integer) stringObjectMap.get("wage");
            String date = (String) stringObjectMap.get("date");
            //筛选掉补班
            if (wage.equals(1)) {
                monthWekDay.remove(date);
            } else {
                monthWekDay.add(date);
            }
        }
        return monthWekDay;
    }

    /**
     * 获取节假日不含周末
     *
     * @param year
     * @param month
     * @return
     */
    private static Map getJjr(int year, int month) {
        String url = "http://timor.tech/api/holiday/year/";
        OkHttpClient client = new OkHttpClient();
        Response response;
        //解密数据
        String rsa = null;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        try {
            response = client.newCall(request).execute();
            rsa = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONObject.parseObject(rsa, Map.class);
    }

    /**
     * 获取周末  月从0开始
     *
     * @param year
     * @param mouth
     * @return
     */
    public static Set<String> getMonthWekDay(int year, int mouth) {
        Set<String> dateList = new HashSet<>();
        SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar(year, mouth, 1);
        Calendar endCalendar = new GregorianCalendar(year, mouth, 1);
        endCalendar.add(Calendar.MONTH, 1);
        while (true) {
            int weekday = calendar.get(Calendar.DAY_OF_WEEK);
            if (weekday == 1 || weekday == 7) {
                dateList.add(simdf.format(calendar.getTime()));
            }
            calendar.add(Calendar.DATE, 1);
            if (calendar.getTimeInMillis() >= endCalendar.getTimeInMillis()) {
                break;
            }
        }
        return dateList;
    }
}
