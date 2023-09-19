package com.platform.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 实体
 * 表名 nideshop_brand
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-19 17:59:15
 */
@Data
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //品牌名称
    private String name;
    //图片
    private String listPicUrl;
    //描述
    private String simpleDesc;
    //图片
    private String picUrl;
    //排序
    private Integer sortOrder;
    //显示
    private Integer isShow;
    //
    private BigDecimal floorPrice;
    //app显示图片
    private String appListPicUrl;
    //新品牌
    private Integer isNew;
    //图片
    private String newPicUrl;
    //排序
    private Integer newSortOrder;
}
