package com.crawler.httpclient;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

/**
 * HttpClient连接池设置
 *
 * @author marxbo
 * @version 1.0
 * @date 2020/2/6 16:09
 */
public class HttpClientPoolTest {

    public static void main(String[] args) throws Exception {
        // 连接池管理器
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        // 设置最大连接数
        cm.setMaxTotal(200);
        // 设置每个主机的并发数
        cm.setDefaultMaxPerRoute(20);

        // 创建HttpClient对象
        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();

        // 创建HttpGet请求
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        // 执行HttpGet请求，返回响应对象
        CloseableHttpResponse response = httpclient.execute(httpGet);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String content = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
            System.out.println("网页内容：\n" + content);
        }
    }

}
