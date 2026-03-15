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
import java.util.Date;

/**
 * 订单实体，对应表：MALL_ORDER。
 * <p>
 * 该实体用于后台订单管理模块的基础 CRUD，保存订单主信息、收货信息、支付信息、金额信息以及时间信息。
 * </p>
 *
 * @author 李鹏军
 * @since 2026-03-15 14:30:00
 */
@Data
@TableName("MALL_ORDER")
public class MallOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID（自增）
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 下单用户ID
     */
    private Integer userId;

    /**
     * 用户昵称（关联字段，不落库）
     */
    @TableField(exist = false)
    private String userNickname;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 物流状态
     */
    private Integer shippingStatus;

    /**
     * 支付状态
     */
    private Integer payStatus;

    /**
     * 收货人姓名
     */
    private String consignee;

    /**
     * 国家编码或名称
     */
    private String country;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区/县
     */
    private String district;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 买家留言
     */
    private String postscript;

    /**
     * 物流方式ID
     */
    private Integer shippingId;

    /**
     * 物流方式名称
     */
    private String shippingName;

    /**
     * 支付方式ID
     */
    private String payId;

    /**
     * 支付方式名称
     */
    private String payName;

    /**
     * 物流费用
     */
    private BigDecimal shippingFee;

    /**
     * 实际支付金额
     */
    private BigDecimal actualPrice;

    /**
     * 使用积分数量
     */
    private Integer integral;

    /**
     * 积分抵扣金额
     */
    private BigDecimal integralMoney;

    /**
     * 订单总价
     */
    private BigDecimal orderPrice;

    /**
     * 商品总价
     */
    private BigDecimal goodsPrice;

    /**
     * 创建时间
     */
    private Date addTime;

    /**
     * 确认时间
     */
    private Date confirmTime;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 运费（整型字段）
     */
    private Integer freightPrice;

    /**
     * 使用优惠券ID
     */
    private Integer couponId;

    /**
     * 父订单ID
     */
    private Integer parentId;

    /**
     * 优惠券抵扣金额
     */
    private BigDecimal couponPrice;

    /**
     * 回调状态（true/false）
     */
    private String callbackStatus;

    /**
     * 物流单号
     */
    private String shippingNo;

    /**
     * 满减金额
     */
    private BigDecimal fullCutPrice;

    /**
     * 订单类型
     */
    private String orderType;
}
