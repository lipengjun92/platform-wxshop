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

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.BlobTypeHandler;

import java.io.Serializable;

/**
 * 实体
 *
 * @author 李鹏军
 * @since 2026-02-24 13:15:28
 */
@Data
@TableName("MALL_GOODS_GALLERY")
public class MallGoodsGalleryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * 商品id
     */
    private Integer goodsId;
    /**
     * 图片
     */
    @TableField(typeHandler = BlobTypeHandler.class)
    private byte[] imgUrl;
    /**
     * 描述
     */
    private String imgDesc;
    /**
     * 排序
     */
    private Integer sortOrder;
}
