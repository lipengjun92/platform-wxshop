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
 * @since 2024-07-19 20:53:33
 */
@Data
@TableName("MALL_FEEDBACK")
public class MallFeedbackEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Integer msgId;
    /**
     * 会员Id
     */
    private Integer userId;
    /**
     * 会员会员名称
     */
    private String userName;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 反馈类型
     */
    private Integer feedType;
    /**
     * 详细内容
     */
    private String content;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 反馈时间
     */
    private Date addTime;
}
