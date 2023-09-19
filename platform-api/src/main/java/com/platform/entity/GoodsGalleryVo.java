package com.platform.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:40
 */
@Data
public class GoodsGalleryVo implements Serializable {
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
}
