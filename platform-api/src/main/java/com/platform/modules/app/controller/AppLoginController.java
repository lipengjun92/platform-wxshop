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
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.annotation.IgnoreAuth;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.common.validator.AbstractAssert;
import com.platform.modules.app.entity.FullUserInfo;
import com.platform.modules.app.request.LoginAppRequest;
import com.platform.modules.app.request.LoginWxMpRequest;
import com.platform.modules.app.request.LoginWxPhoneRequest;
import com.platform.modules.app.request.LoginWxRequest;
import com.platform.modules.app.vo.LoginVo;
import com.platform.modules.wx.entity.WxUserEntity;
import com.platform.modules.wx.service.WxUserService;
import com.platform.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

/**
 * APP登录授权
 *
 * @author 李鹏军
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/auth")
@Tag(name = "AppLoginController|APP登录接口")
public class AppLoginController extends AppBaseController {
    private final WxUserService userService;
    private final JwtUtils jwtUtils;
    private final WxMaService wxMaService;
    private final WxMpService wxMpService;

    /**
     * 微信小程序登录
     *
     * @param request JSON格式参数
     * @return RestResponse
     */
    @IgnoreAuth
    @PostMapping("LoginByMa")
    @Operation(summary = "微信小程序登录", description = "wx.login()每次返回的code只能使用一次")
    public RestResponse<LoginVo> loginByMa(@RequestBody LoginWxRequest request) {
        FullUserInfo fullUserInfo = request.getUserInfo();
        AbstractAssert.isNull(fullUserInfo, "登录失败：userInfo为空");

        String code = request.getCode();
        AbstractAssert.isBlank(code, "登录失败：code为空");

        try {
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            // 用户信息校验
            log.info("》》》微信返回sessionData：" + session.toString());

            if (!wxMaService.getUserService().checkUserInfo(session.getSessionKey(), fullUserInfo.getRawData(), fullUserInfo.getSignature())) {
                log.error("登录失败：数据签名验证失败");
                return RestResponse.fail("登录失败");
            }

            // 解密用户信息
            WxMaUserInfo wxMpUser = wxMaService.getUserService().getUserInfo(session.getSessionKey(), fullUserInfo.getEncryptedData(), fullUserInfo.getIv());

            WxUserEntity user = userService.getOne(new QueryWrapper<WxUserEntity>().eq("UNION_ID", session.getUnionid()));
            if (user == null) {
                user = userService.getOne(new QueryWrapper<WxUserEntity>().eq("MA_OPENID", session.getOpenid()));
            }
            // 没有数据即新注册用户
            if (user == null) {
                user = new WxUserEntity();
                user.setAllIntegral(0);
                user.setIntegral(0);
                user.setRegisterTime(new Date());
                user.setRegisterIp(getClientIp());
            }
            user.setLastLoginTime(new Date());
            user.setLastLoginIp(getClientIp());
            user.setUnionId(session.getUnionid());
            user.setMaOpenid(session.getOpenid());
            //性别 0：未知、1：男、2：女
            user.setSex(Integer.parseInt(wxMpUser.getGender()));
            user.setNickname(wxMpUser.getNickName());
            user.setHeadImgUrl(wxMpUser.getAvatarUrl());
            user.setCity(wxMpUser.getCity());
            user.setProvince(wxMpUser.getProvince());
            userService.saveOrUpdate(user);

            String token = jwtUtils.generateToken(user.getId());

            if (StringUtils.isNullOrEmpty(token)) {
                log.error("登录失败：token生成异常");
                return RestResponse.fail("登录失败");
            }
            LoginVo map = new LoginVo();
            map.setToken(token);
            map.setUserInfo(user);
            map.setUserId(user.getId());
            return RestResponse.ok(map);
        } catch (WxErrorException e) {
            log.error("登录失败：" + e.getMessage());
            return RestResponse.fail("登录失败");
        }
    }

    /**
     * 微信小程序手机号授权
     *
     * @param request JSON格式参数
     * @return RestResponse
     */
    @IgnoreAuth
    @PostMapping("LoginByMaPhone")
    @Operation(summary = "微信小程序手机号授权", description = "wx.login()每次返回的code只能使用一次")
    public RestResponse<String> loginByMaPhone(@RequestBody LoginWxPhoneRequest request) {
        String code = request.getCode();
        AbstractAssert.isBlank(code, "手机号授权失败：code为空");
        String userId = request.getUserId();
        AbstractAssert.isBlank(userId, "userId为空");

        try {
            // 解密用户信息
            WxMaPhoneNumberInfo wxMpUser = wxMaService.getUserService().getPhoneNoInfo(code);

            WxUserEntity user = userService.getById(userId);
            user.setPhone(wxMpUser.getPurePhoneNumber());
            userService.updateById(user);
            return RestResponse.ok("手机号授权成功");
        } catch (WxErrorException e) {
            log.error("手机号授权失败：" + e.getMessage());
            return RestResponse.fail("手机号授权失败");
        }
    }

    /**
     * 微信公众号登录
     *
     * @param request JSON格式参数
     * @return RestResponse
     */
    @IgnoreAuth
    @PostMapping("loginByMp")
    @Operation(summary = "微信公众号登录", description = "根据微信code登录，每一个code只能使用一次")
    public RestResponse<LoginVo> loginByMp(@RequestBody LoginWxMpRequest request) {
        String code = request.getCode();
        AbstractAssert.isBlank(code, "登录失败：code为空");

        String mpAppId = request.getMpAppId();
        AbstractAssert.isBlank(mpAppId, "登录失败：mpAppId为空");

        if (!wxMpService.switchover(mpAppId)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", mpAppId));
        }

        LoginVo map = new LoginVo();
        try {
            WxOAuth2AccessToken auth2AccessToken = wxMpService.getOAuth2Service().getAccessToken(code);

            String openId = auth2AccessToken.getOpenId();

            //获取微信用户信息
            WxOAuth2UserInfo wxMpUser = wxMpService.getOAuth2Service().getUserInfo(auth2AccessToken, null);

            //保存或者更新
            WxUserEntity user = userService.getOne(new QueryWrapper<WxUserEntity>().eq("UNION_ID", wxMpUser.getUnionId()));
            if (user == null) {
                user = userService.getOne(new QueryWrapper<WxUserEntity>().eq("OPENID", wxMpUser.getOpenid()));
            }
            // 没有数据即新注册用户
            if (user == null) {
                user = new WxUserEntity();
                user.setAllIntegral(0);
                user.setIntegral(0);
                user.setRegisterTime(new Date());
                user.setRegisterIp(getClientIp());
            }
            user.setLastLoginTime(new Date());
            user.setLastLoginIp(getClientIp());
            user.setUnionId(wxMpUser.getUnionId());
            user.setOpenid(wxMpUser.getOpenid());
            user.setNickname(wxMpUser.getNickname());
            user.setHeadImgUrl(wxMpUser.getHeadImgUrl());

            Boolean subscribe = wxMpService.getUserService().userInfo(openId).getSubscribe();
            user.setSubscribe(subscribe);

            userService.saveOrUpdate(user);

            //生成token
            String token = jwtUtils.generateToken(user.getId());

            map.setToken(token);
            map.setUserInfo(user);
            map.setUserId(user.getId());
        } catch (WxErrorException e) {
            log.error("登录失败：" + e.getMessage());
            return RestResponse.fail("登录失败");
        }

        return RestResponse.ok(map);
    }

    /**
     * APP端微信登录
     *
     * @param request JSON格式参数
     * @return RestResponse
     */
    @IgnoreAuth
    @PostMapping("AppLoginByWx")
    @Operation(summary = "APP端微信登录", description = "APP端微信登录")
    public RestResponse<LoginVo> appLoginByWx(@RequestBody LoginAppRequest request) {
        WxOAuth2AccessToken auth2AccessToken = request.getUserInfo();

        AbstractAssert.isNull(auth2AccessToken, "登录失败：userInfo为空");

        try {
            //获取微信用户信息
            WxOAuth2UserInfo wxMpUser = wxMpService.getOAuth2Service().getUserInfo(auth2AccessToken, null);

            WxUserEntity user = userService.getOne(new QueryWrapper<WxUserEntity>().eq("UNION_ID", wxMpUser.getUnionId()));
            // 没有数据即新注册用户
            if (user == null) {
                user = new WxUserEntity();
                user.setAllIntegral(0);
                user.setIntegral(0);
                user.setRegisterTime(new Date());
                user.setRegisterIp(getClientIp());
            }
            user.setLastLoginTime(new Date());
            user.setLastLoginIp(getClientIp());
            user.setUnionId(wxMpUser.getUnionId());
            user.setNickname(wxMpUser.getNickname());
            user.setHeadImgUrl(wxMpUser.getHeadImgUrl());
            userService.saveOrUpdate(user);

            String token = jwtUtils.generateToken(user.getId());

            if (StringUtils.isNullOrEmpty(token)) {
                log.error("登录失败：token生成异常");
                return RestResponse.fail("登录失败");
            }
            LoginVo map = new LoginVo();
            map.setToken(token);
            map.setUserInfo(user);
            map.setUserId(user.getId());
            return RestResponse.ok(map);
        } catch (WxErrorException e) {
            log.error("登录失败：" + e.getMessage());
            return RestResponse.fail("登录失败");
        }
    }

    /**
     * 创建调用jsapi时所需要的签名
     *
     * @param url url
     * @return RestResponse
     */
    @IgnoreAuth
    @GetMapping("/createJsapiSignature/{appid}")
    @Operation(summary = "创建调用jsapi时所需要的签名", description = "创建调用jsapi时所需要的签名",
            parameters = {@Parameter(required = true, in = ParameterIn.QUERY, name = "url", description = "url", example = "https://fly2you.cn")}
    )
    public RestResponse<WxJsapiSignature> createJsapiSignature(@PathVariable String appid, @RequestParam String url) {
        WxJsapiSignature data = null;
        try {
            data = wxMpService.switchoverTo(appid).createJsapiSignature(url);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return RestResponse.ok(data);
    }

    /**
     * code静默登录
     *
     * @param code code
     * @return RestResponse
     */
    @IgnoreAuth
    @GetMapping("/{code}")
    @Operation(summary = "静默登录", description = "使用code静默登录",
            parameters = {@Parameter(in = ParameterIn.PATH, required = true, name = "code", description = "code", example = "oxaA11ulr9134oBL9Xscon5at_Gc")}
    )
    public RestResponse<LoginVo> loginByCode(@PathVariable String code) {
        try {
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);

            String openid = session.getOpenid();
            String unionid = session.getUnionid();
            WxUserEntity user = userService.getOne(new QueryWrapper<WxUserEntity>().eq("UNION_ID", session.getUnionid()));
            if (user == null) {
                user = userService.getOne(new QueryWrapper<WxUserEntity>().eq("MA_OPENID", openid));
            }

            if (Objects.isNull(user)) {
                // 用户不存在，新增用户
                user = new WxUserEntity();
                user.setAllIntegral(0);
                user.setIntegral(0);
                user.setRegisterTime(new Date());
                user.setRegisterIp(getClientIp());
                user.setLastLoginTime(new Date());
                user.setLastLoginIp(getClientIp());
                user.setUnionId(unionid);
                user.setMaOpenid(openid);
                userService.save(user);
            }

            String token = jwtUtils.generateToken(user.getId());

            if (StringUtils.isNullOrEmpty(token)) {
                log.error("登录失败：token生成异常");
                return RestResponse.fail("登录失败");
            }
            LoginVo map = new LoginVo();
            map.setToken(token);
            map.setUserInfo(user);
            map.setUserId(user.getId());
            return RestResponse.ok(map);
        } catch (WxErrorException e) {
            log.error("登录失败", e);
            return RestResponse.fail("登录失败：" + e.getError().getErrorMsg());
        }
    }

}
