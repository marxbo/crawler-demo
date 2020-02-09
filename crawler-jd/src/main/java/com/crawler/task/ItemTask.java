package com.crawler.task;

import com.crawler.entity.Item;
import com.crawler.service.ItemService;
import com.crawler.util.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 爬取商品定时任务
 *
 * @author marxbo
 * @version 1.0
 * @date 2020/2/8 18:27
 */
@Component
@EnableScheduling
public class ItemTask {

    /**
     * URL
     */
    public static final String URL = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&cid2=653&cid3=655&s=5760&click=0&page=";

    /**
     * MAPPER
     */
    public static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private HttpUtils httpUtils;

    @Autowired
    private ItemService itemService;

    /**
     * 每隔100秒执行定时任务
     */
    //@Async
    @Scheduled(fixedDelay = 1000000)
    public void process() throws IOException {
        for (int i = 1; i < 10; i += 2) {
            // 获取爬取的HTML页面
            String content = new HttpUtils().doGetHtml(URL + i);
            // 解析HTML页面
            this.parseHtml(content);
        }
    }

    /**
     * 解析HTML页面
     *
     * @param content HTML字符串
     */
    private void parseHtml(String content) throws IOException {
        Document doc = Jsoup.parse(content);
        Elements elements = doc.select("div#J_goodsList > ul > li");

        // 遍历SPU列表
        for (Element spuEle : elements) {
            // 商品spuId
            Long spuId = Long.valueOf(spuEle.attr("data-spu"));

            // SKU列表
            Elements skuEles = spuEle.select("li.ps-item img");
            for (Element skuEle : skuEles) {
                Item item = new Item();
                item.setSku(Long.valueOf(skuEle.attr("data-sku")));

                // 根据SKU判断商品是否被抓取过
                List<Item> list = itemService.findAll(item);
                if (!CollectionUtils.isEmpty(list)) {
                    continue;
                }

                item.setSpu(spuId);
                item.setUrl("https://item.jd.com/" + item.getSku() + ".html");

                // 获取商品标题
                String itemHtml = this.httpUtils.doGetHtml(item.getUrl());
                String title = Jsoup.parse(itemHtml)
                        .select("div.sku-name")
                        .text();
                item.setTitle(title);

                // 格式：[{"cbf":"0","id":"J_100007926772","m":"9999.00","op":"2199.00","p":"1989.00"}]
                String priceJson = this.httpUtils
                        .doGetHtml("https://p.3.cn/prices/mgets?skuIds=J_" + item.getSku());
                double price = MAPPER.readTree(priceJson)
                        .get(0)
                        .get("p")
                        .asDouble();
                item.setPrice(price);

                // http://img10.360buyimg.com/n7/jfs/t1/73194/6/8463/291578/5d672b54Edf32576e/ab951edb84f06f19.jpg
                // http://img10.360buyimg.com/n9/jfs/t1/73194/6/8463/291578/5d672b54Edf32576e/ab951edb84f06f19.jpg
                String img = "http://" + skuEle.attr("data-lazy-img").replace("/n9/", "/n7/");
                // 下载图片
                // this.httpUtils.doGetImage(img);
                item.setPic(img);

                Date now = new Date();
                item.setCreated(now);
                item.setUpdated(now);

                itemService.save(item);
            }
        }
    }

}
