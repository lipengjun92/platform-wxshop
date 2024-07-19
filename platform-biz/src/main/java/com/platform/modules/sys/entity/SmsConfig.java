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
package com.platform.modules.sys.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 名称：SmsConfig <br>
 * 描述：短信配置信息<br>
 *
 * @author 李鹏军
 * @version 1.0
 * @since 1.0.0
 */
@Data
public class SmsConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 类型 1：腾讯云SMS 2：阿里云
     */
    private Integer type;

    /**
     * 腾讯云SMS appid
     */
    private int appid;

    /**
     * 腾讯云SMS key
     */
    private String appkey;

    /**
     * 腾讯云SMS 模板ID
     */
    private Integer templateId;

    /**
     * 阿里云SMS accessKeyId
     */
    private String accessKeyId;
    /**
     * 阿里云SMS accessSecret
     */
    private String accessSecret;

    /**
     * 阿里云SMS 模板code
     */
    private String templateCode;
    /**
     * 签名
     */
    private String sign;
}
