package com.platform.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 实体
 * 表名 nideshop_goods_bargain
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-28 08:35:00
 */
public class GoodsBargainVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //砍价大图
    private String title;
    //活动条例图片
    private String item_pic_url;
    // 原价
    private BigDecimal market_price;
    //
    private Integer goods_id;
    //
    private Integer sort_order;
    //
    private String subtitle;
    //最低拉取几人
    private Integer assist_count;
    // 商品价格
    private BigDecimal retail_price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItem_pic_url() {
        return item_pic_url;
    }

    public void setItem_pic_url(String item_pic_url) {
        this.item_pic_url = item_pic_url;
    }

    public BigDecimal getMarket_price() {
        return market_price;
    }

    public void setMarket_price(BigDecimal market_price) {
        this.market_price = market_price;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public Integer getSort_order() {
        return sort_order;
    }

    public void setSort_order(Integer sort_order) {
        this.sort_order = sort_order;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Integer getAssist_count() {
        return assist_count;
    }

    public void setAssist_count(Integer assist_count) {
        this.assist_count = assist_count;
    }

    public BigDecimal getRetail_price() {
        return retail_price;
    }

    public void setRetail_price(BigDecimal retail_price) {
        this.retail_price = retail_price;
    }
}
