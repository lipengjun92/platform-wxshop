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
 * 订单商品明细实体，对应表：MALL_ORDER_GOODS。
 * <p>
 * 该实体用于维护订单中的商品明细项，记录商品快照、购买数量、成交价及规格信息。
 * </p>
 *
 * @author 李鹏军
 * @since 2026-03-15 14:30:00
 */
@Data
@TableName("MALL_ORDER_GOODS")
public class MallOrderGoodsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID（自增）
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 商品ID
     */
    private Integer goodsId;

    /**
     * 商品名称（下单快照）
     */
    private String goodsName;

    /**
     * 商品编码
     */
    private String goodsSn;

    /**
     * 货品ID
     */
    private Integer productId;

    /**
     * 购买数量
     */
    private Integer number;

    /**
     * 市场价
     */
    private BigDecimal marketPrice;

    /**
     * 零售价
     */
    private BigDecimal retailPrice;

    /**
     * 规格值文本
     */
    private String goodsSpecifitionNameValue;

    /**
     * 是否真实商品（0 否 / 1 是）
     */
    private Integer isReal;

    /**
     * 规格ID串
     */
    private String goodsSpecifitionIds;

    /**
     * 商品图片
     */
    private String listPicUrl;
}
