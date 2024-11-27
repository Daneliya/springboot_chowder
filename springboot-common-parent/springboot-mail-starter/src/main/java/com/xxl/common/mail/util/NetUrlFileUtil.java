package com.xxl.common.mail.util;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络文件读取工具
 *
 * @Author: xxl
 * @Date: 2024/11/27 11:44
 */
@Slf4j
public class NetUrlFileUtil {

    /**
     * 远程读取文件
     *
     * @param netUrl 文件地址
     * @return 返回结果
     */
    public static File getNetUrl(String netUrl) {
        //判断http和https
        File file = null;
        if (netUrl.startsWith("https://")) {
            file = getNetUrlHttps(netUrl);
        } else {
            file = getNetUrlHttp(netUrl);
        }
        return file;
    }

    /**
     * 根据地址获取文件
     *
     * @param netUrl 文件地址
     * @return 返回结果
     */
    public static File getNetUrlHttp(String netUrl) {
        //对本地文件命名
        String fileName = reloadFile(netUrl);
        File file = null;

        URL urlFile;
        InputStream inStream = null;
        OutputStream os = null;
        try {
            file = File.createTempFile("net_url", fileName);
            //下载
            urlFile = new URL(netUrl);
            inStream = urlFile.openStream();
            os = new FileOutputStream(file);

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            log.error("拦截到异常,远程图片获取错误:url,{},Exception{}", netUrl, e);
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
                if (null != inStream) {
                    inStream.close();
                }

            } catch (Exception e) {
                log.error("拦截到异常,远程图片获取错误:url,{},Exception,{}", netUrl, e);
            }
        }
        return file;
    }

    /**
     * 下载文件到本地(支持https)
     *
     * @param fileUrl 远程地址
     * @return 返回结果
     */
    public static File getNetUrlHttps(String fileUrl) {
        //对本地文件进行命名
        String file_name = reloadFile(fileUrl);
        File file = null;

        DataInputStream in = null;
        DataOutputStream out = null;
        try {
            file = File.createTempFile("net_url", file_name);

            SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
            sslcontext.init(null, new TrustManager[]{new X509TrustUtil()}, new java.security.SecureRandom());
            URL url = new URL(fileUrl);
            HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslsession) {
                    log.error("拦截到异常，远程图片获取错误：Hostname is not matched for cert.");
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
            HttpsURLConnection urlCon = (HttpsURLConnection) url.openConnection();
            urlCon.setConnectTimeout(6000);
            urlCon.setReadTimeout(6000);
            int code = urlCon.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                log.error("拦截到异常，文件读取失败");
            }
            // 读文件流
            in = new DataInputStream(urlCon.getInputStream());
            out = new DataOutputStream(new FileOutputStream(file));
            byte[] buffer = new byte[2048];
            int count = 0;
            while ((count = in.read(buffer)) > 0) {
                out.write(buffer, 0, count);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            log.error("拦截到异常,远程图片获取错误:url,{},Exception,{}", fileUrl, e);
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
                if (null != in) {
                    in.close();
                }
            } catch (Exception e2) {
                log.error("拦截到异常,远程图片获取错误:url,{},Exception,{}", fileUrl, e2);
            }
        }
        return file;
    }


    /**
     * 重命名，UUID
     *
     * @param oleFileName 旧名称
     * @return 返回新名称
     */
    public static String reloadFile(String oleFileName) {
        oleFileName = getFileName(oleFileName);
        if (StringUtils.isEmpty(oleFileName)) {
            return oleFileName;
        }
        // 得到后缀
        if (!oleFileName.contains(".")) {
            // 对于没有后缀的文件，直接返回重命名
            return IdUtil.fastSimpleUUID();
        }
        String[] arr = oleFileName.split("\\.");
        // 根据uuid重命名图片
        return IdUtil.fastSimpleUUID() + "." + arr[arr.length - 1];
    }

    /**
     * 把带路径的文件地址解析为真实文件名
     * 例：/25h/upload/hc/1448089199416_06cc07bf-7606-4a81-9844-87d847f8740f.mp4 解析为 1448089199416_06cc07bf-7606-4a81-9844-87d847f8740f.mp4
     *
     * @param url 文件地址
     * @return 返回文件名称
     */
    public static String getFileName(final String url) {
        if (StringUtils.isEmpty(url)) {
            return url;
        }
        String newUrl = url;
        newUrl = newUrl.split("[?]")[0];
        String[] bb = newUrl.split("/");
        return bb[bb.length - 1];
    }

    /**
     * 获取文件后缀
     *
     * @param url 文件地址
     * @return 返回文件名称
     */
    public static String getFileFormat(final String url) {
        if (StringUtils.isEmpty(url)) {
            return url;
        }
        String newUrl = url;
        newUrl = newUrl.split("[?]")[0];
        String[] bb = newUrl.split("\\.");
        return "." + bb[bb.length - 1];
    }

}
