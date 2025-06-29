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
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体
 *
 * @author 李鹏军
 * @since 2024-07-19 21:57:50
 */
@Data
@TableName("MALL_CATEGORY")
public class MallCategoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private String keywords;
    /**
     *
     */
    private String frontDesc;
    /**
     *
     */
    private Integer parentId;
    /**
     *
     */
    private Integer sortOrder;
    /**
     *
     */
    private Integer showIndex;
    /**
     *
     */
    private Integer isShow;
    /**
     *
     */
    private String bannerUrl;
    /**
     *
     */
    private String iconUrl;
    /**
     *
     */
    private String imgUrl;
    /**
     *
     */
    private String wapBannerUrl;
    /**
     *
     */
    private String level;
    /**
     *
     */
    private Integer type;
    /**
     *
     */
    private String frontName;
    /**
     * 父节点名称
     */
    @TableField(exist = false)
    private String parentName;
}
