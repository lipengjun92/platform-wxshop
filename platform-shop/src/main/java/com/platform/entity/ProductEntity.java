package com.platform.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 实体
 * 表名 nideshop_product
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-30 14:31:21
 */
@Data
public class ProductEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //商品Id
    private Integer goodsId;
    //商品规格ids
    private String goodsSpecificationIds;
    //商品序列号
    private String goodsSn;
    //商品库存
    private Integer goodsNumber;
    //零售价格
    private BigDecimal retailPrice;
    //市场价格
    private BigDecimal marketPrice;

    /**
     * 翻译用字段
     */
    //商品
    private String goodsName;
    private String specificationValue;
}
