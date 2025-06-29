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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 实体
 *
 * @author 李鹏军
 * @since 2024-07-19 21:57:59
 */
@Data
@TableName("MALL_BRAND")
public class MallBrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 品牌名称
     */
    private String name;
    /**
     * 图片
     */
    private String listPicUrl;
    /**
     * 描述
     */
    private String simpleDesc;
    /**
     * 图片
     */
    private String picUrl;
    /**
     * 排序
     */
    private Integer sortOrder;
    /**
     * 显示
     */
    private Integer isShow;
    /**
     *
     */
    private BigDecimal floorPrice;
    /**
     * app显示图片
     */
    private String appListPicUrl;
    /**
     * 新品牌
     */
    private Integer isNew;
    /**
     * 图片
     */
    private String newPicUrl;
    /**
     * 排序
     */
    private Integer newSortOrder;
}
