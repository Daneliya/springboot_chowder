package com.xxl.config;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/05/18 10:42
 * @Version: 1.0
 */
@Service
public class WxWorkSendMsg {

    private static CloseableHttpClient httpClient;
    private static HttpPost httpPost;//用于提交登陆数据
    private static HttpGet httpGet;//用于获得登录后的页面
    //CorpID  企业ID
    //AGENTID 应用的ID
    //Secret 应用的ID对应的密钥
    public static final String CONTENT_TYPE = "Content-Type";
    public static final Integer AGENTID = 1000002;
    public static final String CORPID = "wwdd2095476f1aa7fb";
    public static final String CORPSECRET = "0tncpko_Mwqs2qj1iOhFKQSLgWwcv8oB6uxZigpwN7c";
    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
    private static Gson gson = new Gson();

    /**
     * @param toUser       用户的ID 格式"UserID1|UserID2|UserID3"
     * @param contentValue 推送消息内容
     * @throws IOException
     */
    public static void sendTextMesg(String toUser, String contentValue) throws IOException {
        String token = getToken(CORPID, CORPSECRET);
        String postData = createPostData(toUser, "text", AGENTID, "content", contentValue);
        String response = post("utf-8", CONTENT_TYPE, "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=", postData, token);
        System.out.println("获取到的token======>" + token);
        System.out.println("请求数据======>" + postData);
        System.out.println("发送微信的响应数据======>" + response);
    }

    public static String getToken(String corpId, String corpSecret) throws IOException {
        String resp = toAuth("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + corpId + "&corpsecret=" + corpSecret);//拼接字符串得到url
        Map<String, Object> map = gson.fromJson(resp,
                new TypeToken<Map<String, Object>>() {
                }.getType());
        System.out.println("获取token信息：" + map);
        return map.get("access_token").toString();
    }

    protected static String toAuth(String Get_Token_Url) throws IOException {

        httpClient = HttpClients.createDefault();
        httpGet = new HttpGet(Get_Token_Url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        System.out.println(response.toString());
        String resp;
        try {
            HttpEntity entity = response.getEntity();
            System.out.println(response.getAllHeaders());
            resp = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }

        return resp;
    }

    private static String createPostData(String touser, String msgtype, int agent_id, String contentKey, String contentValue) {
        Map<String, Object> weChatData = new HashMap<>();
        weChatData.put("touser", touser);
        weChatData.put("agentid", agent_id);
        weChatData.put("msgtype", msgtype);
        Map<Object, Object> content = new HashMap<Object, Object>();
        content.put(contentKey, contentValue + "\n--------\n" + df.format(new Date()));
        weChatData.put("text", content);
        System.out.println(gson.toJson(weChatData));
        return gson.toJson(weChatData);
    }

    private static String post(String charset, String contentType, String url, String data, String token) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        httpPost = new HttpPost(url + token);
        httpPost.setHeader(CONTENT_TYPE, contentType);
        httpPost.setEntity(new StringEntity(data, charset));
        CloseableHttpResponse response = httpclient.execute(httpPost);
        String resp;
        try {
            HttpEntity entity = response.getEntity();
            resp = EntityUtils.toString(entity, charset);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        return resp;
    }

    private static final String USER_INFO_API_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";


    public static String getUserInfo(String appId) throws IOException {
//        String urlStr = USER_INFO_API_URL + "?access_token=" + getAccessToken(appId);
//        URL url = new URL(urlStr);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
//        StringBuilder builder = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            builder.append(line);
//        }
//        reader.close();
//        conn.disconnect();
//        String resp = toAuth(USER_INFO_API_URL + "?access_token=" + getAccessToken(appId));
        String resp = toAuth(USER_INFO_API_URL + "?access_token=" + getToken(CORPID, CORPSECRET));
        Map<String, Object> map = gson.fromJson(resp,
                new TypeToken<Map<String, Object>>() {
                }.getType());
        System.out.println("获取token信息：" + map);
        return map.toString();
    }

    public static String getAccessToken(String appId) throws IOException {
        // 在企业微信开放平台中注册的应用，可以获取到一个有效的 Access Token。将该 Token 作为参数传递给用户信息 API。
//        String accessTokenUrl = "https://qyapi.weixin.qq.com/cgi-bin/oauth2/access_token?grant_type=client_credential&appid=" + appId + "&secret=";
//        URL url = new URL(accessTokenUrl);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("POST");
//        conn.setDoOutput(true);
//        conn.getOutputStream().write(accessTokenUrl.getBytes(StandardCharsets.UTF_8));
        String resp = "https://qyapi.weixin.qq.com/cgi-bin/oauth2/access_token?grant_type=client_credential&appid=" + appId + "&secret=";
        Map<String, Object> map = gson.fromJson(resp,
                new TypeToken<Map<String, Object>>() {
                }.getType());
        System.out.println("获取token信息：" + map);
        return map.get("access_token").toString();
    }

    public static void getAccessToken222() throws IOException {
        String token = getToken(CORPID, CORPSECRET);
        String resp = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=" + token + "&id=";
        String toAuth = toAuth(resp);
        Map<String, Object> map = gson.fromJson(toAuth,
                new TypeToken<Map<String, Object>>() {
                }.getType());
        System.out.println("获取信息：" + map);
    }
}