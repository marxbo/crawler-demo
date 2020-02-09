package com.crawler.jsoup;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Jsoup选择器解析
 *
 * @author marxbo
 * @version 1.0
 * @date 2020/2/8 16:08
 */
public class ParseSelectorTest {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://www.cnblogs.com/");
        CloseableHttpResponse response = httpClient.execute(httpGet);

        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity, Charsets.UTF_8);
        response.close();  //记得关闭response

        Document document = Jsoup.parse(content);
        // selecotr选择器查找元素
        Elements elements = document.select("#post_list .post_item .post_item_body h3 a");
        for(Element e: elements) {
            System.out.println("博客标题：" + e.text());
            System.out.println("博客地址：" + e.attr("href"));
            System.out.println("target:" + e.attr("target"));
        }

        Element element = document.select("#friend_link").first();
        System.out.println("=> text：\n" + element.text());
        System.out.println("=> html：\n" + element.html());
    }

}
