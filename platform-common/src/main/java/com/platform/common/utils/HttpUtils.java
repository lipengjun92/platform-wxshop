package com.platform.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author 李鹏军
 */
public class HttpUtils {
    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url    发送请求的 URL
     * @param params 请求的参数集合
     * @return 远程资源的响应结果
     * @throws Exception Exception
     */
    @SuppressWarnings("unused")
    public static String sendPost(String url, String params) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(params));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        response.close();
        httpClient.close();
        return responseContent;
    }

    /**
     * 获取网络资源到InputStream
     *
     * @param imgUrl imgUrl
     * @return InputStream
     * @throws Exception Exception
     */
    public static InputStream getNetResource(String imgUrl) throws Exception {
        URL url = new URL(imgUrl);
        URLConnection conn = url.openConnection();
        return conn.getInputStream();
    }
}
