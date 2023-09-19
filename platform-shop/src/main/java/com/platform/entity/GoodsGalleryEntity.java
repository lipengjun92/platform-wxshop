package com.platform.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * 商品顶部轮播图
 * 表名 nideshop_goods_gallery
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-23 14:41:43
 */
@Data
public class GoodsGalleryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //商品id
    private Integer goodsId;
    //图片
    private String imgUrl;
    //描述
    private String imgDesc;
    //排序
    private Integer sortOrder;

    /**
     * 翻译用字段
     */
    //商品
    private String goodsName;
}
