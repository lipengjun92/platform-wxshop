package com.platform.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 商品对应规格表值表实体
 * 表名 nideshop_goods_specification
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-31 11:15:55
 */
@Data
public class GoodsSpecificationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //商品
    private Integer goodsId;
    //规范Id
    private Integer specificationId;
    //规范说明
    private String value;
    //规范图片
    private String picUrl;

    /**
     * 翻译用字段
     */
    //商品
    private String goodsName;
    //规范
    private String specificationName;
}
