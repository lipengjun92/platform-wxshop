package com.platform.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * 实体
 * 表名 nideshop_attribute
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-17 16:48:17
 */
@Data
public class AttributeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Integer id;
    //所属种类
    private Integer attributeCategoryId;
    //名称
    private String name;
    //类型
    private Integer inputType;
    //值
    private String value;
    //排序
    private Integer sortOrder;

    /**
     * 翻译用字段
     */
    //所属种类名称
    private String categoryName;
}
