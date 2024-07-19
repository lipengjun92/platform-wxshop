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
package com.platform.modules.app.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import cn.binarywang.wx.miniapp.constant.WxMaConstants;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import com.platform.common.utils.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 小程序验证服务器
 *
 * @author 李鹏军
 */
@Tag(name = "WxMaPortalController|小程序服务器配置控制器")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/wx/ma/portal/{appid}")
public class WxMaPortalController {

    private final WxMaService wxMaService;
    private final WxMaMessageRouter wxMaMessageRouter;

    @Operation(summary = "认证小程序服务器配置", description = "认证小程序服务器配置")
    @GetMapping(produces = "text/plain;charset=utf-8")
    public String authMaGet(@PathVariable String appid,
                            @RequestParam(name = "signature", required = false) String signature,
                            @RequestParam(name = "timestamp", required = false) String timestamp,
                            @RequestParam(name = "nonce", required = false) String nonce,
                            @RequestParam(name = "echostr", required = false) String echostr) {
        log.info("\n接收到来自微信服务器的认证消息：signature = [{}], timestamp = [{}], nonce = [{}], echostr = [{}]",
                signature, timestamp, nonce, echostr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        if (!this.wxMaService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }

        if (wxMaService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }

        return "非法请求";
    }

    @Operation(hidden = true)
    @PostMapping(produces = "application/xml; charset=UTF-8")
    public String post(@PathVariable String appid,
                       @RequestBody String requestBody,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature,
                       @RequestParam(name = "encrypt_type", required = false) String encryptType,
                       @RequestParam(name = "signature", required = false) String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce) {
        log.info("\n接收微信请求：[msg_signature=[{}], encrypt_type=[{}], signature=[{}]," +
                        " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                msgSignature, encryptType, signature, timestamp, nonce, requestBody);

        if (!this.wxMaService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }

        final boolean isJson = Objects.equals(wxMaService.getWxMaConfig().getMsgDataFormat(),
                WxMaConstants.MsgDataFormat.JSON);
        if (StringUtils.isBlank(encryptType)) {
            // 明文传输的消息
            WxMaMessage inMessage;
            if (isJson) {
                inMessage = WxMaMessage.fromJson(requestBody);
            } else {//xml
                inMessage = WxMaMessage.fromXml(requestBody);
            }

            this.route(inMessage);
            return "success";
        }

        if (Constant.AES.equals(encryptType)) {
            // 是aes加密的消息
            WxMaMessage inMessage;
            if (isJson) {
                inMessage = WxMaMessage.fromEncryptedJson(requestBody, wxMaService.getWxMaConfig());
            } else {//xml
                inMessage = WxMaMessage.fromEncryptedXml(requestBody, wxMaService.getWxMaConfig(),
                        timestamp, nonce, msgSignature);
            }

            this.route(inMessage);
            return "success";
        }

        throw new RuntimeException("不可识别的加密类型：" + encryptType);
    }

    private void route(WxMaMessage message) {
        try {
            this.wxMaMessageRouter.route(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
