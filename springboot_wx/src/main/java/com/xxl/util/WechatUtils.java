//package com.xxl.util;
//
//import com.alibaba.fastjson.JSONObject;
//import com.xxl.constants.WechatConstants;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.security.MessageDigest;
//import java.util.Random;
//
///**
// * @Description:
// * @Author: xxl
// * @Date: 2023/05/18 15:03
// * @Version: 1.0
// */
//public class WechatUtils {
//
//    private static final Logger logger = LoggerFactory.getLogger(WechatUtils.class);
//
//    /**
//     * 获取微信token
//     *
//     * @param
//     * @return
//     * @throws Exception
//     */
//    public static JSONObject getToken(String type) throws Exception {
//        String url = "";
//        if ("corp".equals(type)) {
//            url = WechatConstants.GETTOKEN_URL.replace("{corpid}", WechatConstants.CORP_ID).replace("{corpsecret}", WechatConstants.CORP_SECRET);
//        } else if ("contacts".equals(type)) {
//            url = WechatConstants.GETTOKEN_URL.replace("{corpid}", WechatConstants.CORP_ID).replace("{corpsecret}", WechatConstants.CONTACTS_SECRET);
//        }
//        JSONObject jsonObject = HttpClientUtil.getJson(url, "", "json", "UTF-8", false);
////        JSONObject jsonObject = HttpClientUtil.formatResponseParamsToJsonObject(jsons);
////        String access_token = jsonObject.getString("access_token");
//        logger.info("\n=====1.获取token请求：" + jsonObject.toString());
//        return jsonObject;
//    }
//
//    /**
//     * 根据code,获取微信用户id
//     *
//     * @param token
//     * @param code
//     * @return
//     * @throws Exception
//     */
//    public static JSONObject getUserId(String token, String code) throws Exception {
//        String url = WechatConstants.GETUSERINFO_URL.replace("{access_token}", token).replace("{code}", code);
//        JSONObject jsonObject = HttpClientUtil.getJson(url, "", "json", "UTF-8", false);
////        JSONObject jsonObject = HttpClientUtil.formatResponseParamsToJsonObject(jsons);
////        String userId = jsonObject.getString("UserId");
//        logger.info("\n=====2.获取用户id请求：" + jsonObject.toString());
//        return jsonObject;
//    }
//
//    /**
//     * 获取单个微信用户信息
//     *
//     * @param token
//     * @param userid
//     * @return
//     * @throws Exception
//     */
//    public static JSONObject getUserByUserId(String token, String userid) throws Exception {
//        String url = WechatConstants.USER_GET_URL.replace("{access_token}", token).replace("{userid}", userid);
//        JSONObject jsonObject = HttpClientUtil.getJson(url, "", "json", "UTF-8", false);
////        JSONObject jsonObject = HttpClientUtil.formatResponseParamsToJsonObject(jsons);
//        logger.info("\n=====3.获取用户信息：" + jsonObject.toString());
//        return jsonObject;
//    }
//
//    /**
//     * 获取某部门用户列表
//     */
//    public static JSONObject getSimpleList(String token, String departmentId, String fetchChild) throws Exception {
//        String url = WechatConstants.USER_SIMPLELIST_URL.replace("{access_token}", token).replace("{department_id}", departmentId).replace("{fetch_child}", fetchChild);
//        JSONObject jsonObject = HttpClientUtil.getJson(url, "", "json", "UTF-8", false);
////        byte[] jsons = HttpClientUtil.get(url, "", "json", "UTF-8", false);
////        JSONObject jsonObject = HttpClientUtil.formatResponseParamsToJsonObject(jsons);
//        logger.info("\n=====4.获取用户列表：" + jsonObject.toString());
//        return jsonObject;
//    }
//
//    /**
//     * 获取某部门列表
//     */
//    public static JSONObject getDepartmentList(String token, String departmentId) throws Exception {
//        String url = WechatConstants.DEPARTMENT_LIST_URL.replace("{access_token}", token).replace("{id}", departmentId);
//        JSONObject jsonObject = HttpClientUtil.getJson(url, "", "json", "UTF-8", false);
//        logger.info("\n=====5.获取部门列表：" + jsonObject.toString());
//        return jsonObject;
//    }
//
//
//    /**
//     * 获取企业的jsapi_ticket
//     */
//    public static JSONObject getJsapiTicket(String token) throws Exception {
//        String url = WechatConstants.GET_JSAPI_TICKET.replace("{access_token}", token);
//        JSONObject jsonObject = HttpClientUtil.getJson(url, "", "json", "UTF-8", false);
//        logger.info("\n=====6.获取JsapiTicket(：" + jsonObject.toString());
//        return jsonObject;
//    }
//
//    /**
//     * 获取指定长度随机字符串
//     *
//     * @param length
//     * @return
//     */
//    public static String getRandomString(int length) {
//        Random random = new Random();
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < length; i++) {
//            int number = random.nextInt(3);
//            long result = 0;
//            switch (number) {
//                case 0:
//                    result = Math.round(Math.random() * 25 + 65);
//                    sb.append(String.valueOf((char) result));
//                    break;
//                case 1:
//                    result = Math.round(Math.random() * 25 + 97);
//                    sb.append(String.valueOf((char) result));
//                    break;
//                case 2:
//                    sb.append(String.valueOf(new Random().nextInt(10)));
//                    break;
//            }
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 拼接字符串
//     */
//    public static String getSignatureStr(JSONObject userJson) {
//        StringBuffer jsapi_ticketStr = new StringBuffer();
//        jsapi_ticketStr.append("jsapi_ticket=").append(WechatScheduled.jsapi_ticket).append("&")
//                .append("noncestr=").append(userJson.getString("nonceStr")).append("&")
//                .append("timestamp=").append(userJson.getString("timestamp")).append("&")
//                .append("url=").append(userJson.getString("url"));
//
//        return jsapi_ticketStr.toString();
//    }
//
//    /**
//     * 获取sha1加密字符串
//     *
//     * @param str
//     * @return
//     */
//
//    public static String getSha1(String str) {
////         return  DigestUtils.sha1Hex(inStr);
//
//        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
//                'a', 'b', 'c', 'd', 'e', 'f'};
//        try {
//            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
//            mdTemp.update(str.getBytes("UTF-8"));
//            byte[] md = mdTemp.digest();
//            int j = md.length;
//            char buf[] = new char[j * 2];
//            int k = 0;
//            for (int i = 0; i < j; i++) {
//                byte byte0 = md[i];
//                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
//                buf[k++] = hexDigits[byte0 & 0xf];
//            }
//            return new String(buf);
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//
//    /**
//     * 推送消息到企业微信
//     */
//    public static JSONObject sendMessage(String token, byte[] body) throws Exception {
//        String url = WechatConstants.SEND_MESSAGE_URL.replace("{access_token}", token);
//        JSONObject jsonObject = HttpClientUtil.postJson(url, "", body, "json", "UTF-8", false);
//        logger.info("\n=====7.推送message：" + jsonObject.toString());
//        return jsonObject;
//    }
//
//}
