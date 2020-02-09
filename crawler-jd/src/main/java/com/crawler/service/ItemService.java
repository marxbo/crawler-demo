package com.crawler.service;

import com.crawler.entity.Item;

import java.util.List;

/**
 * 商品Service接口
 *
 * @author marxbo
 * @version 1.0
 * @date 2020/2/8 17:54
 */
public interface ItemService {

    /**
     * 根据条件查询商品列表
     *
     * @param item
     * @return
     */
    List<Item> findAll(Item item);

    /**
     * 保存商品
     *
     * @param item
     */
    void save(Item item);

}
