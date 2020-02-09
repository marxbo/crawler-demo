package com.crawler.jsoup;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Jsoup解析HTML
 *
 * @author marxbo
 * @version 1.0
 * @date 2020/2/7 21:20
 */
public class ParseHtmlTest {

    public static void main(String[] args) throws Exception {
        parseUrl();
        System.out.println("----------------------------------------------------");
        parseFile();
        System.out.println("----------------------------------------------------");
        parseString();
    }

    /**
     * (1)Jsoup解析URL
     */
    public static void parseUrl() throws IOException {
        /**
         * Jsoup虽然可以替代HttpClient直接发起请求解析数据，
         * 但是实际开发需要【多线程、连接池、代理】等方式，
         * 而Jsoup对这些的支持不是很好，所以【仅作为HTML解析工具】使用。
         */
        Document doc = Jsoup.parse(new URL("https://www.baidu.com/"), 5000);
        Element e = doc.getElementsByTag("title").first();
        System.out.println(e.text());
    }

    /**
     * (2)Jsoup解析html文件
     */
    public static void parseFile() throws IOException {
        String path = ParseHtmlTest.class.getResource("/").getPath();
        File file = new File(path + "test.html");
        Document doc = Jsoup.parse(file, Charsets.UTF_8.toString());
        Element e = doc.getElementsByClass("post_item").first();
        // 只打印标签内的内容
        System.out.println(e.text());
        // 打印标签内的html
        System.out.println(e.html());
    }

    /**
     * (3)Jsoup解析字符串
     */
    public static void parseString() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://www.cnblogs.com/");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String content = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
            Document doc = Jsoup.parse(content);
            Element e = doc.getElementById("site_nav_top");
            System.out.println(e.text());
        }
    }

}
