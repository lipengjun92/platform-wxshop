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
import java.util.Date;

/**
 * 实体
 *
 * @author 李鹏军
 * @since 2024-07-19 21:02:38
 */
@Data
@TableName("MALL_USER_COUPON")
public class MallUserCouponEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    private Integer id;
    /**
     * 
     */
    private Integer couponId;
    /**
     * 
     */
    private String couponNumber;
    /**
     * 
     */
    private Integer userId;
    /**
     * 
     */
    private Date usedTime;
    /**
     * 
     */
    private Date addTime;
    /**
     * 
     */
    private Integer orderId;
    /**
     * 状态 1. 可用 2. 已用 3. 过期
     */
    private Integer couponStatus;
    /**
     * 来源key
     */
    private String sourceKey;
    /**
     * 发券人
     */
    private Integer referrer;
}
