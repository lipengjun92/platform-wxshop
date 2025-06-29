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
import java.math.BigDecimal;

/**
 * 实体
 *
 * @author 李鹏军
 * @since 2024-07-19 20:53:40
 */
@Data
@TableName("MALL_CART")
public class MallCartEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 会员Id
     */
    private Integer userId;
    /**
     * sessionId
     */
    private String sessionId;
    /**
     * 商品Id
     */
    private Integer goodsId;
    /**
     * 商品序列号
     */
    private String goodsSn;
    /**
     * 产品Id
     */
    private Integer productId;
    /**
     * 产品名称
     */
    private String goodsName;
    /**
     * 市场价
     */
    private BigDecimal marketPrice;
    /**
     * 零售价格
     */
    private BigDecimal retailPrice;
    /**
     * 数量
     */
    private Integer number;
    /**
     * 规格属性组成的字符串，用来显示用
     */
    private String goodsSpecifitionNameValue;
    /**
     * product表对应的goods_specifition_ids
     */
    private String goodsSpecifitionIds;
    /**
     *
     */
    private Integer checked;
    /**
     * 商品图片
     */
    private String listPicUrl;
    /**
     * 微信昵称
     */
    @TableField(exist = false)
    private String nickname;
}
