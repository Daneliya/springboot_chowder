//package com.xxl.config;
//
///**
// * @Description:
// * @Author: xxl
// * @Date: 2023/05/18 0:23
// * @Version: 1.0
// */
//public class WeWorkApiClient {
//    private static final String APP_ID = "your_app_id"; // 企业微信应用ID    1000002
//    private static final String API_KEY = "your_api_key"; // 企业微信API Key    0tncpko_Mwqs2qj1iOhFKQSLgWwcv8oB6uxZigpwN7c
//    private static final String ACCESS_TOKEN = "your_access_token"; // 企业微信Access Token
//
//    public static WxService getService() throws Exception {
//        WxServiceImpl service = new WxServiceImpl();
//        AccessTokenResponse accessTokenResponse = service.getAccessToken(APP_ID, API_KEY, null);
//        if (accessTokenResponse != null && accessTokenResponse.getErrcode() == 0) {
//            ACCESS_TOKEN = accessTokenResponse.getAccess_token();
//        } else {
//            throw new Exception("Failed to get access token from WeWork");
//        }
//        return service;
//    }
//}
