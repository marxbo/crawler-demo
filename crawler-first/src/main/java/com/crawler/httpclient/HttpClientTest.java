package com.crawler.httpclient;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Restful请求-无参
 *
 * @author marxbo
 * @version 1.0
 * @date 2020/2/5 21:12
 */
public class HttpClientTest {

    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            // Get请求
            HttpGet httpGet = new HttpGet("http://www.baidu.com");
            // Post请求
            // HttpPost httpPost = new HttpPost("http://www.baidu.com");
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
