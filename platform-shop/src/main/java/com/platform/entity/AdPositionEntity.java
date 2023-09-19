package com.platform.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * 实体
 * 表名 nideshop_ad_position
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-19 12:02:42
 */
@Data
public class AdPositionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //位置名称
    private String name;
    //宽度
    private Integer width;
    //高度
    private Integer height;
    //描述
    private String desc;
}
