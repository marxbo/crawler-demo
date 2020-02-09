package com.crawler.httpclient;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.Charsets;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Post请求-带参
 *      (1)URI参数：与HttpGet一样，拼接到uri地址后面
 *      (2)表单参数：HttpEntity为UrlEncodedFormEntity
 *      (3)JSON参数：HttpEntity为StringEntity
 *
 * @author marxbo
 * @version 1.0
 * @date 2020/2/6 13:39
 */
public class HttpPostParamTest {

    public static void main(String[] args) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();
            // 创建表单参数的HttpPost
            HttpPost httpPost = createHttpPostForm();
            // 创建json参数的HttpPost
            // HttpPost httpPost = createHttpPostJson();
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String content = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
                System.out.println("网页内容：\n" + content);
            }
        } catch (Exception e) {
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

    /**
     * 创建HttpPost对象-表单参数 + URI参数
     *
     * @return HttpPost对象
     * @throws URISyntaxException
     */
    private static HttpPost createHttpPostForm() throws URISyntaxException {
        // 创建HttpPost对象(拼接uri参数)
        URI uri = new URIBuilder("http://localhost:8080")
                .setParameter("wd", "爬虫")
                .build();
        HttpPost httpPost = new HttpPost(uri);

        // 封装表单参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("key", "value"));

        // 创建表单Entity(需要编码)
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, Charsets.UTF_8);

        // 设置请求体为表单
        httpPost.setEntity(formEntity);

        return httpPost;
    }

    /**
     * 创建HttpPost对象-JSON参数
     *
     * @return HttpPost对象
     */
    private static HttpPost createHttpPostJson() {
        // 创建HttpPost对象(拼接uri参数)
        HttpPost httpPost = new HttpPost("http://localhost:8080");

        // 设置请求头(application/json;charset=utf8)
        httpPost.setHeader("Content-Type", ContentType.APPLICATION_JSON.toString());

        // 设置请求体为JSON字符串
        String str = JSON.toJSONString(new User("marxbo", 20));
        httpPost.setEntity(new StringEntity(str, Charsets.UTF_8));

        return httpPost;
    }

    static class User {

        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

}
