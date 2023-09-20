package com.platform.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * 实体
 * 表名 nideshop_attribute_category
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-17 16:13:27
 */
@Data
public class AttributeCategoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer id;
    //
    private String name;
    //
    private Integer enabled;
}
