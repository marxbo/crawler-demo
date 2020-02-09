package com.crawler.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * HttpClient下载文件
 *
 * @author marxbo
 * @version 1.0
 * @date 2020/2/7 15:17
 */
public class HttpGetFileTest {

    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://www.baidu.com/img/baidu_jgylogo3.gif");
        httpGet.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        CloseableHttpResponse response = null;
        response = httpClient.execute(httpGet);
        InputStream in = null;
        OutputStream out = null;
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            System.out.println("内容类型：" + entity.getContentType().getValue());
            in = entity.getContent();
            File file = new File("D:\\baidu.gif");
            out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = in.read(buf)) != -1) {
                out.write(buf,0, len);
            }
        }
        in.close();
        out.close();
        response.close();
        httpClient.close();
    }

}
