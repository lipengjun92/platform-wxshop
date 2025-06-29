/*
 *
 *      Copyright (c) 2018-2099, lipengjun All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the fly2you.cn developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lipengjun (939961241@qq.com)
 *
 */
package com.platform.modules.mall.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体
 *
 * @author 李鹏军
 * @since 2025-06-29 18:07:36
 */
@Data
@TableName("MALL_ATTRIBUTE")
public class MallAttributeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * 商品类型
     */
    private Integer attributeCategoryId;
    /**
     * 属性名称
     */
    private String name;
    /**
     * 当添加商品时,该属性的添加类别; 0为手功输入;1为选择输入;2为多行文本输入
     */
    private Integer inputType;
    /**
     * 即选择输入,则attr_name对应的值的取值就是该这字段值
     */
    private String value;
    /**
     *
     */
    private Integer sortOrder;
    /**
     * 即选择输入,则attr_name对应的值的取值就是该这字段值
     */
    @TableField(exist = false)
    private String categoryName;
}
