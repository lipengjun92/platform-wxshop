package com.platform.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:41
 */
public class ProductVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //商品Id
    private Integer goods_id;
    //商品规格ids
    private String goods_specification_ids;
    //商品序列号
    private String goods_sn;
    //商品库存
    private Integer goods_number;
    //零售价格
    private BigDecimal market_price;
    //时长价
    private BigDecimal retail_price;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_specification_ids() {
        return goods_specification_ids;
    }

    public void setGoods_specification_ids(String goods_specification_ids) {
        this.goods_specification_ids = goods_specification_ids;
    }

    public String getGoods_sn() {
        return goods_sn;
    }

    public void setGoods_sn(String goods_sn) {
        this.goods_sn = goods_sn;
    }

    public Integer getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(Integer goods_number) {
        this.goods_number = goods_number;
    }

    public BigDecimal getRetail_price() {
        return retail_price;
    }

    public void setRetail_price(BigDecimal retail_price) {
        this.retail_price = retail_price;
    }

    public BigDecimal getMarket_price() {
        return market_price;
    }

    public void setMarket_price(BigDecimal market_price) {
        this.market_price = market_price;
    }
}
