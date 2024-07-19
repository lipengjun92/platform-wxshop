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
import com.platform.modules.wx.service.MsgReplyService;
import com.platform.modules.wx.service.WxUserService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 微信公众号消息处理（用户关注）
 *
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
@RequiredArgsConstructor
public class SubscribeHandler extends AbstractHandler {

    private final MsgReplyService msgReplyService;

    private final WxUserService userService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) throws WxErrorException {
        this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser() + "，事件：" + wxMessage.getEventKey());

        userService.refreshUserInfo(wxMessage.getFromUser());

        msgReplyService.tryAutoReply(true, wxMessage.getFromUser(), wxMessage.getEvent());

        // 处理特殊事件，如用户扫描带参二维码关注
        if (StringUtils.hasText(wxMessage.getEventKey())) {
            msgReplyService.tryAutoReply(true, wxMessage.getFromUser(), wxMessage.getEventKey());
        }
        try {
            return new TextBuilder().build("fromUser：" + wxMessage.getFromUser() + "；自动回复：您好，微同商城商业版，销售价格两万元，提供所有源码以及文档！如有需要请加客服微信（15209831990）咨询",
                    wxMessage, weixinService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    protected WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage) {
        this.logger.info("特殊请求-新关注用户 OPENID: " + wxMessage.getFromUser());
        //对关注事件和扫码事件分别处理
        userService.refreshUserInfo(wxMessage.getFromUser());
        msgReplyService.tryAutoReply(true, wxMessage.getFromUser(), wxMessage.getEvent());
        if (StringUtils.hasText(wxMessage.getEventKey())) {
            msgReplyService.tryAutoReply(true, wxMessage.getFromUser(), wxMessage.getEventKey());
        }
        return null;
    }
}
