package com.platform.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * 实体
 * 表名 nideshop_shipping
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-09-04 21:42:24
 */
@Data
public class ShippingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer id;
    //
    private String code;
    //
    private String name;
}
