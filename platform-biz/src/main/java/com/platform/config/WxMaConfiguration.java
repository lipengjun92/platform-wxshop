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
package com.platform.config;

import cn.binarywang.wx.miniapp.api.WxMaAnalysisService;
import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.api.WxMaQrcodeService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaMsgServiceImpl;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaCodeLineColor;
import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.config.impl.WxMaRedisBetterConfigImpl;
import cn.binarywang.wx.miniapp.message.WxMaMessageHandler;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import com.platform.common.utils.Constant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.redis.RedisTemplateWxRedisOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.File;

/**
 * wechat ma configuration
 *
 * @author 李鹏军
 */
@AllArgsConstructor
@Slf4j
@Configuration
@EnableConfigurationProperties(WxMaProperties.class)
public class WxMaConfiguration {
    @Autowired
    private final WxMaProperties properties;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public WxMaService wxMaService() {
        WxMaService wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(wxMaConfig());
        return wxMaService;
    }

    @Bean
    public WxMaQrcodeService wxMaQrcodeService() {
        return wxMaService().getQrcodeService();
    }

    @Bean
    public WxMaAnalysisService wxMaAnalysisService() {
        return wxMaService().getAnalysisService();
    }

    @Bean
    public WxMaMsgService wxMaMsgService(WxMaService service) {
        return new WxMaMsgServiceImpl(service);
    }

    @Bean
    public WxMaRedisBetterConfigImpl wxMaConfig() {
        WxMaRedisBetterConfigImpl wxMaRedisBetterConfig = new WxMaRedisBetterConfigImpl(new RedisTemplateWxRedisOps(stringRedisTemplate), Constant.WX_MA_CONFIG);
        wxMaRedisBetterConfig.setAppid(properties.getAppid());
        wxMaRedisBetterConfig.setSecret(properties.getSecret());
        wxMaRedisBetterConfig.setToken(properties.getToken());
        wxMaRedisBetterConfig.setAesKey(properties.getAesKey());
        wxMaRedisBetterConfig.setMsgDataFormat(properties.getMsgDataFormat());
        return wxMaRedisBetterConfig;
    }

    @Bean
    public WxMaMessageRouter newRouter(WxMaService service) {
        final WxMaMessageRouter router = new WxMaMessageRouter(service);
        router.rule().handler(logHandler).next()
                .rule().async(false).content("旗舰版").handler(textHandler).end()
                .rule().async(false).content("推广码").handler(qrcodeHandler).end();
        return router;
    }

    private final WxMaMessageHandler logHandler = (wxMessage, context, service, sessionManager) -> {
        System.out.println("收到消息：" + wxMessage.toString());
        service.getMsgService().sendKefuMsg(
                WxMaKefuMessage.newTextBuilder().content("fromUser：" + wxMessage.getFromUser() + "；自动回复：您好，微同商城商业版，销售价格两万元，提供所有源码以及文档！如有需要请加客服微信（15209831990）咨询")
                        .toUser(wxMessage.getFromUser()).build());
        return null;
    };

    private final WxMaMessageHandler textHandler = (wxMessage, context, service, sessionManager) -> {
        service.getMsgService().sendKefuMsg(
                WxMaKefuMessage.newTextBuilder().content("您好，旗舰版支持多商户模式，使用uni-app重构移动端，支持iOS、Android、H5、以及微信小程序等多个平台。如有需要请加客服微信（15209831990）咨询。")
                        .toUser(wxMessage.getFromUser()).build());
        return null;
    };

    private final WxMaMessageHandler qrcodeHandler = (wxMessage, context, service, sessionManager) -> {
        try {
            String page = "/pages/index/index?referrer=" + wxMessage.getFromUser();
            final File file = service.getQrcodeService().createWxaCode(page, "release", 500, false, new WxMaCodeLineColor("0", "0", "0"), true);
            WxMediaUploadResult uploadResult = service.getMediaService().uploadMedia("image", file);
            service.getMsgService().sendKefuMsg(
                    WxMaKefuMessage
                            .newImageBuilder()
                            .mediaId(uploadResult.getMediaId())
                            .toUser(wxMessage.getFromUser())
                            .build());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return null;
    };

}
