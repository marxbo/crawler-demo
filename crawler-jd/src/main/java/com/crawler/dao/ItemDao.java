package com.crawler.dao;

import com.crawler.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 商品DAO
 *
 * @author marxbo
 * @version 1.0
 * @date 2020/2/8 17:42
 */
public interface ItemDao extends JpaRepository<Item, Long> {

}
