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
 * @since 2026-02-24 13:15:28
 */
@Data
@TableName("MALL_GOODS")
public class MallGoodsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private Integer categoryId;
    /**
     *
     */
    private String goodsSn;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private Integer brandId;
    /**
     *
     */
    private Integer goodsNumber;
    /**
     *
     */
    private String keywords;
    /**
     *
     */
    private String goodsBrief;
    /**
     *
     */
    private String goodsDesc;
    /**
     *
     */
    private Integer isOnSale;
    /**
     *
     */
    private Date addTime;
    /**
     *
     */
    private Integer sortOrder;
    /**
     *
     */
    private Integer isDelete;
    /**
     *
     */
    private Integer attributeCategory;
    /**
     * 专柜价格
     */
    private BigDecimal counterPrice;
    /**
     * 附加价格
     */
    private BigDecimal extraPrice;
    /**
     *
     */
    private Integer isNew;
    /**
     * 商品单位
     */
    private String goodsUnit;
    /**
     * 商品主图
     */
    private String primaryPicUrl;
    /**
     * 商品列表图
     */
    private String listPicUrl;
    /**
     * 零售价格
     */
    private BigDecimal retailPrice;
    /**
     * 销售量
     */
    private Integer sellVolume;
    /**
     * 主sku　product_id
     */
    private Integer primaryProductId;
    /**
     * 单位价格，单价
     */
    private BigDecimal unitPrice;
    /**
     *
     */
    private String promotionDesc;
    /**
     *
     */
    private String promotionTag;
    /**
     * APP专享价
     */
    private BigDecimal appExclusivePrice;
    /**
     * 是否是APP专属
     */
    private Integer isAppExclusive;
    /**
     *
     */
    private Integer isLimited;
    /**
     *
     */
    private Integer isHot;
    /**
     *
     */
    private BigDecimal marketPrice;
    /**
     * 创建人ID
     */
    private Long createUserId;
    /**
     * 修改人ID
     */
    private Long updateUserId;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     *
     */
    private Long createUserDeptId;
}
