package com.platform.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:40
 */
@Data
public class GoodsAttributeVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //商品Id
    private Integer goodsId;
    //属性Id
    private Integer attributeId;
    //属性值
    private String value;
    // 冗余
    private String name;
}
