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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.platform.common.utils.StringUtils;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Long userId;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addTime;

    /**
     * 确认时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date confirmTime;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
    @TableField(exist = false)
    private Integer goodsCount;
    //订单状态的处理
    @TableField(exist = false)
    private String orderStatusText;
    //可操作的选项
    @TableField(exist = false)
    private Map<String, Object> handleOption;
    //区县
    @TableField(exist = false)
    private String fullRegion;
    @TableField(exist = false)
    private List<MallOrderGoodsEntity> goodsList;

    public String getFullRegion() {
        if (StringUtils.isNotBlank(this.fullRegion)) {
            return fullRegion;
        } else {
            StringBuilder strBuff = new StringBuilder();
            if (StringUtils.isNotBlank(this.country)) {
                strBuff.append(this.country).append(" ");
            }
            if (StringUtils.isNotBlank(this.province)) {
                strBuff.append(this.province).append(" ");
            }
            if (StringUtils.isNotBlank(this.city)) {
                strBuff.append(this.city).append(" ");
            }
            if (StringUtils.isNotBlank(this.district)) {
                strBuff.append(this.district).append(" ");
            }
            this.fullRegion = strBuff.toString();
            return this.fullRegion;
        }
    }

    public String getOrderStatusText() {
        if (null != orderStatus && StringUtils.isEmpty(orderStatusText)) {
            switch (orderStatus) {
                case 0:
                    orderStatusText = "未付款";
                    break;
                case 201:
                    orderStatusText = "等待发货";
                    break;
                case 300:
                    orderStatusText = "待收货";
                    break;
                case 301:
                    orderStatusText = "已完成";
                    break;
                case 200:
                    orderStatusText = "已付款";
                    break;
                case 101:
                    orderStatusText = "已取消";
                    break;
                case 401:
                    orderStatusText = "已取消";
                    break;
                case 402:
                    orderStatusText = "已退货";
                    break;
                default:
                    orderStatusText = "未付款";
            }
        }
        return orderStatusText;
    }

    public Map<String, Object> getHandleOption() {
        handleOption = new HashMap<>();
        //取消操作
        handleOption.put("cancel", false);
        //删除操作
        handleOption.put("delete", false);
        //支付操作
        handleOption.put("pay", false);
        //评论操作
        handleOption.put("comment", false);
        //确认收货操作
        handleOption.put("delivery", false);
        //完成订单操作
        handleOption.put("confirm", false);
        //退换货操作
        handleOption.put("return", false);
        //再次购买
        handleOption.put("buy", false);

        //订单流程：　下单成功－》支付订单－》发货－》收货－》评论
        //订单相关状态字段设计，采用单个字段表示全部的订单状态
        //1xx 表示订单取消和删除等状态 0订单创建成功等待付款，　101订单已取消，　102订单已删除
        //2xx 表示订单支付状态　201订单已付款，等待发货
        //3xx 表示订单物流相关状态　300订单已发货， 301用户确认收货
        //4xx 表示订单退换货相关的状态　401 没有发货，退款　402 已收货，退款退货

        //如果订单已经取消或是已完成，则可删除和再次购买
        if (orderStatus == 101) {
//            handleOption.put("delete", true);
            handleOption.put("buy", true);
        }

        //如果订单没有被取消，且没有支付，则可支付，可取消
        if (orderStatus == 0) {
            handleOption.put("cancel", true);
            handleOption.put("pay", true);
        }

        //如果订单已付款，没有发货，则可退款操作
        if (orderStatus == 201) {
            handleOption.put("cancel", true);
        }

        //如果订单已经发货，没有收货，则可收货操作和退款、退货操作
        if (orderStatus == 300) {
//            handleOption.put("cancel", true);
            handleOption.put("confirm", true);
//            handleOption.put("return", true);
        }

        //如果订单已经支付，且已经收货，则可完成交易、评论和再次购买
        if (orderStatus == 301) {
            handleOption.put("comment", true);
            handleOption.put("buy", true);
        }
        return handleOption;
    }
}
