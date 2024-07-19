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

import com.platform.modules.wx.entity.WxMsgEntity;
import com.platform.modules.wx.service.MsgReplyService;
import com.platform.modules.wx.service.WxMsgService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 微信公众号消息处理（消息处理）
 *
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
@RequiredArgsConstructor
public class MsgHandler extends AbstractHandler {

    private final MsgReplyService msgReplyService;
    private final WxMsgService wxMsgService;
    private static final String TRANSFER_CUSTOMER_SERVICE_KEY = "人工";

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {

        String textContent = wxMessage.getContent();
        String fromUser = wxMessage.getFromUser();
        boolean autoReplyed = msgReplyService.tryAutoReply(false, fromUser, textContent);
        //当用户输入关键词如“人工”，并且有客服在线时，把消息转发给在线客服
        if (TRANSFER_CUSTOMER_SERVICE_KEY.equals(textContent) || !autoReplyed) {
            wxMsgService.addWxMsg(WxMsgEntity.buildOutMsg(WxConsts.KefuMsgType.TRANSFER_CUSTOMER_SERVICE, fromUser, null));
            return WxMpXmlOutMessage
                    .TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUser())
                    .toUser(fromUser).build();
        }
        return null;
    }
}
