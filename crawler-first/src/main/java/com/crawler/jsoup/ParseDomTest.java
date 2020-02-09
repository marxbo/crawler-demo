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

import java.io.IOException;

/**
 * DOM遍历文档
 *
 * @author marxbo
 * @version 1.0
 * @date 2020/2/8 15:15
 */
public class ParseDomTest {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://www.cnblogs.com/");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String content = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
            Document doc = Jsoup.parse(content);

            // 1、根据【id】获取元素
            Element e1 = doc.getElementById("site_nav_top");
            System.out.println("元素ID：" + e1.id());
            printLine("宗旨", e1);

            // 2、根据【标签】获取元素
            Element e2 = doc.getElementsByTag("title").first();
            System.out.println("元素标签：" + e2.tagName());
            printLine("标题", e2);

            // 3、根据【class】获取元素
            Element e3 = doc.getElementsByClass("post_item").first();
            System.out.println("元素class：" + e2.className());
            printLine("博客简介html", e3);

            // 4、根据【属性】获取元素
            Element e4 = doc.getElementsByAttributeValue("class", "titlelnk").first();
            System.out.println("元素class属性值：" + e4.attr("class"));
            printLine("博客标题", e4);
        }
    }

    public static void printLine(String str, Element e) {
        System.out.println(str + "：\n" + e.html());
        System.out.println("-------------------------------------------------------------");
    }

}
