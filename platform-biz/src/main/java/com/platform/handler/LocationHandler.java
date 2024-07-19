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
package com.platform.handler;

import com.platform.builder.TextBuilder;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 微信公众号消息处理（地理位置）
 *
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class LocationHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        //上报地理位置事件
        this.logger.info("上报地理位置，msgType:" + wxMessage.getMsgType() + "，纬度 : {}，经度 : {}，精度 : {}",
                wxMessage.getLatitude(), wxMessage.getLongitude(), wxMessage.getPrecision());

        String content = "感谢反馈，您的的地理位置已收到！纬度 : " + wxMessage.getLatitude() + "，经度 : " + wxMessage.getLongitude() + "，精度 : " + wxMessage.getPrecision();
        return new TextBuilder().build(content, wxMessage, wxMpService);
    }

}
