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
package com.platform.modules.wx.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.wx.entity.WxQrCodeEntity;
import com.platform.modules.wx.form.WxQrCodeForm;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

import java.util.Map;

/**
 * 公众号带参二维码
 *
 * @author 李鹏军
 * @date 2021-05-06 18:30:15
 */
public interface WxQrCodeService extends IService<WxQrCodeEntity> {
    /**
     * 分页查询用户数据
     *
     * @param params 查询参数
     * @return PageUtils 分页结果
     */
    Page<WxQrCodeEntity> queryPage(Map<String, Object> params);

    /**
     * 创建公众号带参二维码
     *
     * @param form
     * @return
     * @throws WxErrorException
     */
    WxMpQrCodeTicket createQrCode(WxQrCodeForm form) throws WxErrorException;
}

