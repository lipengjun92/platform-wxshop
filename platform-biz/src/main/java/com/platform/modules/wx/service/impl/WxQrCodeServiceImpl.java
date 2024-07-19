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
package com.platform.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.wx.dao.WxQrCodeDao;
import com.platform.modules.wx.entity.WxQrCodeEntity;
import com.platform.modules.wx.form.WxQrCodeForm;
import com.platform.modules.wx.service.WxQrCodeService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author 李鹏军
 * @date 2021-05-06 18:30:15
 */
@Service("wxQrCodeService")
@RequiredArgsConstructor
public class WxQrCodeServiceImpl extends ServiceImpl<WxQrCodeDao, WxQrCodeEntity> implements WxQrCodeService {

    private final WxMpService wxService;

    @Override
    public Page<WxQrCodeEntity> queryPage(Map<String, Object> params) {
        String sceneStr = (String) params.get("sceneStr");
        Page<WxQrCodeEntity> page = new Query<WxQrCodeEntity>(params).getPage();
        return baseMapper.selectPage(page,
                new QueryWrapper<WxQrCodeEntity>()
                        .like(StringUtils.hasText(sceneStr), "SCENE_STR", sceneStr));
    }

    /**
     * 创建公众号带参二维码
     *
     * @param form
     * @return
     */
    @Override
    public WxMpQrCodeTicket createQrCode(WxQrCodeForm form) throws WxErrorException {
        WxMpQrCodeTicket ticket;
        if (form.getIsTemp()) {
            //创建临时二维码
            ticket = wxService.getQrcodeService().qrCodeCreateTmpTicket(form.getSceneStr(), form.getExpireSeconds());
        } else {
            //创建永久二维码
            ticket = wxService.getQrcodeService().qrCodeCreateLastTicket(form.getSceneStr());
        }
        WxQrCodeEntity wxQrCode = new WxQrCodeEntity(form);
        wxQrCode.setTicket(ticket.getTicket());
        wxQrCode.setUrl(ticket.getUrl());
        if (form.getIsTemp()) {
            wxQrCode.setExpireTime(new Date(System.currentTimeMillis() + ticket.getExpireSeconds() * 1000L));
        }
        this.save(wxQrCode);
        return ticket;
    }

}
