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
package com.platform.modules.wx.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.modules.wx.form.WxQrCodeForm;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公众号带参二维码
 *
 * @author 李鹏军
 * @date 2021-05-06 18:30:15
 */
@Data
@TableName("WX_QR_CODE")
public class WxQrCodeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private String id;
    /**
     * 二维码类型
     */
    private Boolean isTemp;
    /**
     * 场景值ID
     */
    private String sceneStr;
    /**
     * 二维码ticket
     */
    private String ticket;
    /**
     * 二维码图片解析后的地址
     */
    private String url;
    /**
     * 该二维码失效时间
     */
    private Date expireTime;
    /**
     * 该二维码创建时间
     */
    private Date createTime;

    public WxQrCodeEntity() {
    }

    public WxQrCodeEntity(WxQrCodeForm form) {
        this.isTemp = form.getIsTemp();
        this.sceneStr = form.getSceneStr();
        this.createTime = new Date();
    }
}
