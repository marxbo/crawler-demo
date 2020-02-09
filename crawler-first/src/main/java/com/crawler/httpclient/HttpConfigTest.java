package com.crawler.httpclient;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 配置HttpClient
 *
 * @author marxbo
 * @version 1.0
 * @date 2020/2/5 20:53
 */
public class HttpConfigTest {

    public static void main(String[] args) throws IOException {
        // 创建HttpClient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建HttpGet请求
        HttpGet httpGet = new HttpGet("http://www.tuicool.com/");

        // 配置请求信息
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(1000)           // 创建连接的最长时间，单位是毫秒
                .setConnectionRequestTimeout(500)  // 设置获取连接的最长时间，单位是毫秒
                .setSocketTimeout(10 * 1000)         // 设置数据传输的最长时间，单位是毫秒
                .build();
        httpGet.setConfig(config);
        httpGet.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");

        // 执行HttpGet请求，返回响应对象
        CloseableHttpResponse response = httpclient.execute(httpGet);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String content = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
            System.out.println("网页内容：\n" + content);
        }
    }

}
