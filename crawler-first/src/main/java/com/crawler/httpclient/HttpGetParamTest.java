package com.crawler.httpclient;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Get请求-带参：
 *      (1)拼接带参的uri
 *      (2)通过URI对象穿件HttpGet
 *
 * @author marxbo
 * @version 1.0
 * @date 2020/2/5 21:36
 */
public class HttpGetParamTest {

    public static void main(String[] args) throws URISyntaxException {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();
            // 通过URIBuilder创建URI对象
            URI uri = new URIBuilder("https://www.baidu.com/s")
                    .setParameter("wd", "爬虫")
                    .build();
            HttpGet httpGet = new HttpGet(uri);
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String content = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
                System.out.println("网页内容：\n" + content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
