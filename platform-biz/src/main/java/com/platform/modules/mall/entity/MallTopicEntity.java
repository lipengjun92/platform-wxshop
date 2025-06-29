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

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 实体
 *
 * @author 李鹏军
 * @since 2025-06-22 12:04:32
 */
@Data
@TableName("MALL_TOPIC")
public class MallTopicEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * 专题主题
     */
    private String title;
    /**
     * 专题内容
     */
    private String content;
    /**
     * 化名
     */
    private String avatar;
    /**
     * 专题条例图片
     */
    private String itemPicUrl;
    /**
     * 子标题
     */
    private String subtitle;
    /**
     * 专题类别
     */
    private Integer topicCategoryId;
    /**
     * 专题价格
     */
    private BigDecimal priceInfo;
    /**
     *
     */
    private String readCount;
    /**
     * 场景图片链接
     */
    private String scenePicUrl;
    /**
     * 专题模板Id
     */
    private Integer topicTemplateId;
    /**
     * 专题标签Id
     */
    private Integer topicTagId;
    /**
     * 主题类别
     */
    @TableField(exist = false)
    private String categoryName;
}
