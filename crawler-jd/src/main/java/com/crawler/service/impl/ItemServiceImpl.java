package com.crawler.service.impl;

import com.crawler.dao.ItemDao;
import com.crawler.entity.Item;
import com.crawler.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品Service实现类
 *
 * @author marxbo
 * @version 1.0
 * @date 2020/2/8 17:55
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;

    @Override
    public List<Item> findAll(Item item) {
        Example<Item> example = Example.of(item);
        return itemDao.findAll(example, Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public void save(Item item) {
        itemDao.save(item);
    }
}
