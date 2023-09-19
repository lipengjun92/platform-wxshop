package com.platform.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:41
 */
@Data
public class AttributeVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer id;
    //
    private Integer attributeCategoryId;
    //
    private String name;
    //
    private Integer inputType;
    //
    private String value;
    //
    private Integer sortOrder;
}
