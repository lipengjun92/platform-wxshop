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
package com.platform.modules.app.entity;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lipengjun
 * @since 2017-08-15 08:03:41
 */
@Data
public class FullUserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * errMsg
     */
    private String errMsg;
    /**
     * rawData
     */
    private String rawData;
    /**
     * data(QQ)
     */
    private String data;
    /**
     * userInfo
     */
    private WxMaUserInfo userInfo;
    /**
     * encryptedData
     */
    private String encryptedData;
    /**
     * iv
     */
    private String iv;
    /**
     * signature
     */
    private String signature;
}
