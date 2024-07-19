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
package com.platform.modules.sys.controller;

import com.platform.common.annotation.SysLog;
import com.platform.common.utils.AesUtil;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.form.SysLoginForm;
import com.platform.modules.sys.service.SysCaptchaService;
import com.platform.modules.sys.service.SysUserService;
import com.platform.modules.sys.service.SysUserTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录相关
 *
 * @author 李鹏军
 */
@Tag(name = "SysLoginController|登录相关")
@RequiredArgsConstructor
@RestController
public class SysLoginController extends AbstractController {

    private final SysUserService sysUserService;
    private final SysUserTokenService sysUserTokenService;
    private final SysCaptchaService sysCaptchaService;

    /**
     * 验证码
     *
     * @param response response
     * @param uuid     uuid
     */
    @Operation(summary = "验证码", description = "验证码")
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //获取图片验证码
        BufferedImage image = sysCaptchaService.getCaptcha(uuid);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 登录
     *
     * @param form 登录表单
     * @return RestResponse
     */
    @Operation(summary = "登录", description = "登录")
    @SysLog("登录")
    @PostMapping("/sys/login")
    public RestResponse<String> login(@RequestBody SysLoginForm form) {
        boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
        if (!captcha) {
            return RestResponse.fail("验证码不正确");
        }

        //用户信息
        SysUserEntity user = sysUserService.queryByUserName(form.getUserName());
        String password;
        try {
            // AESUtil.desEncrypt解密后 password 为char[16], trim转为char[6]
            password = AesUtil.desEncrypt(form.getPassword()).trim();
        } catch (Exception e) {
            return RestResponse.fail("解密失败！");
        }

        // 账号不存在
        if (user == null) {
            return RestResponse.fail("账号或密码不正确");
        }

        //账号锁定
        if (user.getStatus() == 0) {
            return RestResponse.fail("账号已被锁定,请联系管理员");
        }

        // 账号不存在
        if (!user.getPassword().equals(new Sha256Hash(password, user.getSalt()).toHex())) {
            return RestResponse.fail("账号或密码不正确");
        }

        //生成token，并保存到数据库
        String token = sysUserTokenService.createToken(user.getUserId());

        return RestResponse.ok("登录成功", token);
    }


    /**
     * 退出系统
     *
     * @return RestResponse
     */
    @Operation(summary = "退出系统", description = "退出系统")
    @PostMapping("/sys/logout")
    public RestResponse<String> logout() {
        sysUserTokenService.logout(getUserId());
        return RestResponse.ok();
    }

}
