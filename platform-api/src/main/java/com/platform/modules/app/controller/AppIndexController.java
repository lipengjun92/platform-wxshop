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

import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.common.utils.*;
import com.platform.config.RedisTemplateUtil;
import com.platform.modules.app.request.PhoneCodeRequest;
import com.platform.modules.app.request.PhoneRequest;
import com.platform.modules.sys.entity.SmsConfig;
import com.platform.modules.sys.entity.SysSmsLogEntity;
import com.platform.modules.sys.service.SysConfigService;
import com.platform.modules.sys.service.SysSmsLogService;
import com.platform.modules.wx.entity.WxUserEntity;
import com.platform.modules.wx.service.WxUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 李鹏军
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/index")
@Tag(name = "AppIndexController|APP首页接口")
public class AppIndexController extends AppBaseController {
    private final SysConfigService sysConfigService;
    private final SysSmsLogService smsLogService;
    private final WxUserService userService;
    private final RedisTemplateUtil redisTemplateUtil;

    /**
     * 根据key获取value
     */
    @IgnoreAuth
    @GetMapping("/getConfigByKey")
    @Operation(summary = "根据key获取value", description = "根据key获取value",
            parameters = {@Parameter(in = ParameterIn.QUERY, name = "key", description = "key", example = "1", required = true)}
    )
    public RestResponse<String> getConfigByKey(@RequestParam String key) {
        String value = sysConfigService.getValue(key);
        return RestResponse.ok("操作成功", value);
    }

    /**
     * 发送短信
     *
     * @param request JSON格式参数
     * @return RestResponse
     */
    @SuppressWarnings("AlibabaMethodTooLong")
    @IgnoreAuth
    @PostMapping("smsCode")
    @Operation(summary = "发送短信", description = "发送短信验证码",
            parameters = {@Parameter(in = ParameterIn.HEADER, name = "token", description = "用户token", required = true)}
    )
    public RestResponse<String> smsCode(@RequestBody PhoneRequest request) {
        String phone = request.getPhone();
        if (!isMobile(phone)) {
            return RestResponse.fail("请输入正确的手机号");
        }
        // 五分钟之内不能重复发送短信
        Object code = redisTemplateUtil.get(Constant.PRE_SMS + phone);
        if (!StringUtils.isNullOrEmpty(code)) {
            return RestResponse.ok("短信已发送");
        }

        //生成验证码
        String smsCode = CharUtil.getRandomNum(4);
        //获取云存储配置信息
        SmsConfig config = sysConfigService.getConfigObject(Constant.SMS_CONFIG_KEY, SmsConfig.class);
        if (StringUtils.isNullOrEmpty(config)) {
            return RestResponse.fail("请先配置短信平台信息");
        }
        if (Objects.equals(config.getType(), Constant.SmsType.TX.getValue())) {
            if (StringUtils.isNullOrEmpty(config.getAppid())) {
                return RestResponse.fail("请先配置短信平台APPID");
            }
            if (StringUtils.isNullOrEmpty(config.getAppkey())) {
                return RestResponse.fail("请先配置短信APP_KEY");
            }
            if (StringUtils.isNullOrEmpty(config.getTemplateId())) {
                return RestResponse.fail("请先配置短信templateId");
            }
        }
        if (Objects.equals(config.getType(), Constant.SmsType.ALI.getValue())) {
            if (StringUtils.isNullOrEmpty(config.getAccessKeyId())) {
                return RestResponse.fail("请先配置短信平台accessKeyId");
            }
            if (StringUtils.isNullOrEmpty(config.getAccessSecret())) {
                return RestResponse.fail("请先配置短信accessSecret");
            }
            if (StringUtils.isNullOrEmpty(config.getTemplateCode())) {
                return RestResponse.fail("请先配置短信templateCode");
            }
        }
        if (StringUtils.isNullOrEmpty(config.getSign())) {
            return RestResponse.fail("请先配置短信平台签名");
        }
        // 短信记录
        SysSmsLogEntity smsLogVo = new SysSmsLogEntity();
        smsLogVo.setCode(smsCode);
        smsLogVo.setMobile(phone);
        smsLogVo.setStime(new Date());
        smsLogVo.setSign(config.getSign());

        // 过期时间
        int expireTime = 15;
        redisTemplateUtil.set(Constant.PRE_SMS + phone, smsCode, expireTime * 60);

        /**
         * 您的验证码是{1}，请于{2}分钟内填写。如非本人操作，请忽略本短信。
         */
        // 腾讯云短信
        if (config.getType() == 1) {
            SmsSingleSenderResult result = SmsUtil.crSendSms(config.getAppid(), config.getAppkey(), "86", phone, config.getTemplateId(), new String[]{smsCode, String.valueOf(expireTime)}, config.getSign());
            smsLogVo.setTemplateId(config.getTemplateId().toString());
            smsLogVo.setReturnMsg(result.errMsg);

            if (result.result == 0) {
                smsLogVo.setSendStatus(result.result);
                smsLogVo.setSendId(result.sid);
                smsLogVo.setSuccessNum(1);
                smsLogService.save(smsLogVo);
            } else {
                smsLogVo.setSuccessNum(0);
                smsLogVo.setSendStatus(1);
                smsLogService.save(smsLogVo);
                return RestResponse.fail("短信发送失败");
            }
        } else {
            // 阿里云短信
            Map<String, Object> params = new HashMap<>(4);
            params.put("code", smsCode);
            params.put("time", expireTime);
            SendSmsResponse response;
            try {
                smsLogVo.setTemplateId(config.getTemplateCode());
                response = SmsUtil.aliSendSms(config.getAccessKeyId(), config.getAccessSecret(), phone, config.getTemplateCode(), params, config.getSign());
                smsLogVo.setReturnMsg(response.getBody().getMessage());
                if (Constant.OK.equals(response.getBody().code)) {
                    smsLogVo.setSendStatus(0);
                    smsLogVo.setSendId(response.getBody().getBizId());
                    smsLogVo.setSuccessNum(1);
                    smsLogService.save(smsLogVo);
                } else {
                    smsLogVo.setSuccessNum(0);
                    smsLogVo.setSendStatus(1);
                    smsLogService.save(smsLogVo);
                    return RestResponse.fail("短信发送失败");
                }
            } catch (Exception e) {
                smsLogVo.setSuccessNum(0);
                smsLogVo.setSendStatus(1);
                smsLogVo.setReturnMsg(e.getMessage());
                smsLogService.save(smsLogVo);
                return RestResponse.fail("短信发送失败：" + e);
            }
        }
        return RestResponse.ok("短信发送成功", smsCode);
    }

    /**
     * 绑定手机
     *
     * @param loginUser 登录用户
     * @param request   JSON格式参数
     * @return RestResponse
     */
    @PostMapping("bindMobile")
    @Operation(summary = "绑定手机", description = "校验验证码绑定手机",
            parameters = {@Parameter(in = ParameterIn.HEADER, name = "token", description = "用户token", required = true)}
    )
    public RestResponse<String> bindMobile(@LoginUser WxUserEntity loginUser, @RequestBody PhoneCodeRequest request) {
        String mobile = request.getMobile();
        String mobileCode = request.getMobileCode();

        if (!isMobile(mobile)) {
            return RestResponse.fail("请输入正确的手机号");
        }
        Object smsCode = redisTemplateUtil.get(Constant.PRE_SMS + mobile);
        if (StringUtils.isNullOrEmpty(smsCode)) {
            return RestResponse.fail("验证码已失效，请重新获取");
        }
        if (!mobileCode.equals(smsCode)) {
            return RestResponse.fail("验证码错误");
        }
        WxUserEntity userVo = userService.getById(loginUser.getOpenid());
        userVo.setPhone(mobile);
        userService.updateById(userVo);

        //验证通过后删除redis中的验证码
        redisTemplateUtil.del(Constant.PRE_SMS + mobile);
        return RestResponse.ok("手机绑定成功");
    }

    /**
     * 验证手机号
     *
     * @param mobile
     * @return
     */
    private boolean isMobile(String mobile) {
        if (StringUtils.isNotBlank(mobile)) {
            String s2 = "^[1](([3][0-9])|([4][5,7,9])|([5][0-9])|([6][6])|([7][3,5,6,7,8])|([8][0-9])|([9][8,9]))[0-9]{8}$";
            Pattern p = Pattern.compile(s2);
            Matcher m = p.matcher(mobile);
            return m.matches();
        }
        return false;
    }
}
