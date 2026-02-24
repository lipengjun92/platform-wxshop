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

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体
 *
 * @author 李鹏军
 * @since 2026-02-24 10:20:41
 */
@Data
@TableName("MALL_COUPON")
public class MallCouponEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    private Integer id;
    /**
     * 
     */
    private String name;
    /**
     * 
     */
    private BigDecimal typeMoney;
    /**
     * 
     */
    private Integer sendType;
    /**
     * 
     */
    private BigDecimal minAmount;
    /**
     * 
     */
    private BigDecimal maxAmount;
    /**
     * 
     */
    private Date sendStartDate;
    /**
     * 
     */
    private Date sendEndDate;
    /**
     * 
     */
    private Date useStartDate;
    /**
     * 
     */
    private Date useEndDate;
    /**
     * 
     */
    private BigDecimal minGoodsAmount;
    /**
     * 转发次数
     */
    private Integer minTransmitNum;
}
