package com.crawler.entity;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 商品实体类
 *
 * @author marxbo
 * @version 1.0
 * @date 2020/2/8 17:14
 */
@Data
@Entity
@Table(name = "jd_item")
public class Item {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标准产品单位（商品集合）
     */
    private Long spu;

    /**
     * 库存量单位（最小品类单元）
     */
    private Long sku;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品价格
     */
    private Double price;

    /**
     * 商品图片
     */
    private String pic;

    /**
     * 商品详情地址
     */
    private String url;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date updated;

}
