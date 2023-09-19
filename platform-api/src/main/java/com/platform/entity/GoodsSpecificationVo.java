package com.platform.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * 商品对应规格表值表
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:40
 */
@Data
public class GoodsSpecificationVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //商品
    private Integer goodsId;
    //规范Id
    private Integer specificationId;
    //规范说明
    private String value;
    private String name;
    //规范图片
    private String picUrl;
}
