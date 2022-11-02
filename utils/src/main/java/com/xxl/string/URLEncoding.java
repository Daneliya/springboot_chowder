package com.xxl.string;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对URL进行UrlEncode处理
 * 参考：https://www.cnblogs.com/hucheng1997/p/11040178.html
 * 参考：https://www.cnblogs.com/wolf-shuai/p/14786158.html
 *
 * @Author: xxl
 * @Date: 2022/10/31 10:26
 */
public class URLEncoding {

    public static void main(String[] args) throws Exception {
        String str = "http://www.baidu.com?wd=中国&language=简体汉语&size=Song14";
        String result = getRealUrl(str);
        Map result2 = getArgs(str);
        System.out.println(result);
        System.out.println(result2);

        String urlEncodeURL = "https://www.lcsxs.com中";
        String s = encode(urlEncodeURL);
        System.out.println(s);
    }

    /**
     * 对url中的参数进行url编码
     *
     * @param str
     * @return
     */
    public static String getRealUrl(String str) {
        try {
            int index = str.indexOf("?");
            if (index < 0) {
                return str;
            }
            String query = str.substring(0, index);
            String params = str.substring(index + 1);
            Map map = getArgs(params);
            String encodeParams = transMapToString(map);
            return query + "?" + encodeParams;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return "";
    }

    /**
     * 将url参数格式转化为map
     *
     * @param params
     * @return
     * @throws Exception
     */
    public static Map getArgs(String params) throws Exception {
        Map map = new HashMap();
        String[] pairs = params.split("&");
        for (int i = 0; i < pairs.length; i++) {
            int pos = pairs[i].indexOf("=");
            if (pos == -1) {
                continue;
            }
            String argname = pairs[i].substring(0, pos);
            String value = pairs[i].substring(pos + 1);
            value = URLEncoder.encode(value, "utf-8");
            map.put(argname, value);
        }
        return map;
    }

    /**
     * 将map转化为指定的String类型
     *
     * @param map
     * @return
     */
    public static String transMapToString(Map map) {
        java.util.Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (java.util.Map.Entry) iterator.next();
            sb.append(entry.getKey().toString()).append("=").append(null == entry.getValue() ? "" :
                    entry.getValue().toString()).append(iterator.hasNext() ? "&" : "");
        }
        return sb.toString();
    }

    /**
     * 将String类型按一定规则转换为Map
     *
     * @param mapString
     * @return
     */
    public static Map transStringToMap(String mapString) {
        Map map = new HashMap();
        java.util.StringTokenizer items;
        for (StringTokenizer entrys = new StringTokenizer(mapString, "&"); entrys.hasMoreTokens();
             map.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null)) {
            items = new StringTokenizer(entrys.nextToken(), "=");
        }
        return map;
    }


    /**
     * 有些中文或者有空格的、传入参数带中文的URL需要用如下方式进行encode方能进行真正的请求
     *
     * @param str
     * @return
     */
    public static String urlEncodeURL(String str) {
        try {
            String result = URLEncoder.encode(str, "UTF-8");
            //+实际上是 空格 url encode而来
            result = result.replaceAll("%3A", ":").replaceAll("%2F", "/").replaceAll("\\+", "%20");
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 只对url地址中的中文进行编码
     *
     * @param url
     * @return
     */
    public static String encode(String url) {
        try {
            Matcher matcher = Pattern.compile("[\\u4e00\\u9fa5]").matcher(url);
            int count = 0;
            while (matcher.find()) {
                System.out.println(matcher.group());
                String tmp = matcher.group();
                url = url.replaceAll(tmp, java.net.URLEncoder.encode(tmp, "gbk"));
                count++;

            }
            System.out.println(count);
            //url = java.net.URLEncoder.encode(url,"gbk");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return url;
    }
}
