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

/**
 * 实体
 *
 * @author 李鹏军
 * @since 2024-07-19 20:53:40
 */
@Data
@TableName("MALL_ADDRESS")
public class MallAddressEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    private Integer id;
    /**
     * 会员ID
     */
    private Integer userId;
    /**
     * 收货人姓名
     */
    private String userName;
    /**
     * 
     */
    private Integer isDefault;
    /**
     * 手机
     */
    private String telNumber;
    /**
     * 邮编
     */
    private String postalCode;
    /**
     * 收货地址国家码
     */
    private String nationalCode;
    /**
     * 省
     */
    private String provinceName;
    /**
     * 市
     */
    private String cityName;
    /**
     * 区
     */
    private String countyName;
    /**
     * 详细收货地址信息
     */
    private String detailInfo;
}
