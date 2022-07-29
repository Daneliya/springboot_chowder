package com.xxl.Number;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成流水号
 * https://www.cnblogs.com/Jermaine/archive/2011/04/07/2008432.html
 */
public class SerialNumber {

    public static void main(String[] args) {
        System.out.println(SerialNumber.newInstance("", new Date()));
    }

    private static final int MAX_VALUE = 99999;
    private static final String FORMAT = "yyyyMMdd";
    private static final Format DF = new SimpleDateFormat(FORMAT);
    private static final byte[] lock = new byte[0];
    private String prefix = null;
    private Date date = null;
    private int number = 1;
    private static Map<String, SerialNumber> map = new HashMap<String, SerialNumber>();

    private SerialNumber(String prefix, Date date) {
        this.prefix = prefix;
        this.date = date;
    }

    public static SerialNumber newInstance(String prefix) {
        Date date = new Date();
        return newInstance(prefix, date);
    }

    public static SerialNumber newInstance(String prefix, Date date) {
        SerialNumber o = null;
        synchronized (lock) {
            String key = getKey(prefix, date);
            if (map.containsKey(key)) {
                o = map.get(key);
                int number = o.getNumber();
                if (number < MAX_VALUE) {
                    o.setNumber(number + 1);
                } else {
                    o.setNumber(1);
                }

            } else {
                o = new SerialNumber(prefix, date);
                map.put(key, o);
            }
        }
        return o;
    }


    private static String getKey(String prefix, Date date) {
        return prefix + format(date);
    }

    private static String format(Date date) {
        return DF.format(date);
    }

    @Override
    public String toString() {
        return prefix + format(date) + String.format("%05d", number);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}