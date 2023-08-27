package com.xxl.config;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/05/18 13:55
 * @Version: 1.0
 */
public class WeChatUtil {

    private static final String GET_ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
    private static final String GET_USER_INFO_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";
    private static final String CONVERT_TO_OPENID_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid";

    private static final String CORP_ID = "your_corp_id";
    private static final String SECRET = "your_secret";

    /**
     * 获取企业微信的AccessToken
     *
     * @return AccessToken
     */
    public static String getAccessToken() {
        String accessToken = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(GET_ACCESS_TOKEN_URL);
            uriBuilder.addParameter("corpid", CORP_ID);
            uriBuilder.addParameter("corpsecret", SECRET);
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            response = httpClient.execute(httpGet);
            String result = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject.containsKey("access_token")) {
                accessToken = jsonObject.getString("access_token");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return accessToken;
    }

    /**
     * 根据code获取用户的UserID
     *
     * @param code 企业微信登录授权码
     * @return UserID
     */
    public static String getUserId(String code) {
        String userId = null;
        String accessToken = getAccessToken();
        if (StringUtils.isNotEmpty(accessToken)) {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = null;
            try {
                URIBuilder uriBuilder = new URIBuilder(GET_USER_INFO_URL);
                uriBuilder.addParameter("access_token", accessToken);
                uriBuilder.addParameter("code", code);
                HttpGet httpGet = new HttpGet(uriBuilder.build());
                response = httpClient.execute(httpGet);
                String result = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.containsKey("UserId")) {
                    userId = jsonObject.getString("UserId");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (response != null) {
                        response.close();
                    }
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return userId;
    }

    /**
     * 根据UserID获取用户的openid
     *
     * @param userId 用户的UserID
     * @return openid
     */
    public static String getOpenId(String userId) {
        String openId = null;
        String accessToken = getAccessToken();
        if (StringUtils.isNotEmpty(accessToken)) {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = null;
            try {
                HttpPost httpPost = new HttpPost(CONVERT_TO_OPENID_URL);
                httpPost.addHeader("Content-Type", "application/json");
                Map<String, String> data = new HashMap<>();
                data.put("userid", userId);
                StringEntity entity = new StringEntity(JSONObject.toJSONString(data), "utf-8");
                httpPost.setEntity(entity);
                URIBuilder uriBuilder = new URIBuilder(CONVERT_TO_OPENID_URL);
                uriBuilder.addParameter("access_token", accessToken);
                httpPost.setURI(uriBuilder.build());
                response = httpClient.execute(httpPost);
                String result = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.containsKey("openid")) {
                    openId = jsonObject.getString("openid");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (response != null) {
                        response.close();
                    }
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return openId;
    }
}
