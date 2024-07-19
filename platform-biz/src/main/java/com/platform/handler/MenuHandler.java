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

import com.platform.modules.wx.service.MsgReplyService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.util.WxMpConfigStorageHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 微信公众号消息处理（菜单事件）
 *
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
@RequiredArgsConstructor
public class MenuHandler extends AbstractHandler {
    private final MsgReplyService msgReplyService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        if (WxConsts.EventType.VIEW.equals(wxMessage.getEvent())) {
            return null;
        }
        String appid = WxMpConfigStorageHolder.get();
        logger.info("菜单事件：" + wxMessage.getEventKey());
        msgReplyService.tryAutoReply(true, wxMessage.getFromUser(), wxMessage.getEventKey());
        return null;
    }
}
