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
package com.platform.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;

import java.util.Map;

/**
 * https://next.api.aliyun.com/api/Dysmsapi/2017-05-25/SendSms?params={}&lang=JAVA
 *
 * @author lipengjun
 */
public class SmsUtil {
    /**
     * 使用AK&SK初始化阿里云SMS账号Client
     *
     * @param accessKeyId     accessKeyId
     * @param accessKeySecret accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static Client createAliSmsClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret)
                .setEndpoint("dysmsapi.aliyuncs.com");
        return new Client(config);
    }

    /**
     * 指定模板ID单发短信
     * 签名参数未提供或者为空时，会使用默认签名发送短信
     *
     * @param accessKeyId     accessKeyId
     * @param accessKeySecret accessKeySecret
     * @param phoneNumber     手机号
     * @param templateCode    模板CODE
     * @param params          参数
     * @param smsSign         签名
     * @return SmsSingleSenderResult
     */
    public static SendSmsResponse aliSendSms(String accessKeyId, String accessKeySecret, String phoneNumber, String templateCode, Map<String, Object> params, String smsSign) throws Exception {
        Client client = SmsUtil.createAliSmsClient(accessKeyId, accessKeySecret);
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        RuntimeOptions runtime = new RuntimeOptions();
        sendSmsRequest.setSignName(smsSign);
        sendSmsRequest.setPhoneNumbers(phoneNumber);
        sendSmsRequest.setTemplateCode(templateCode);
        sendSmsRequest.setTemplateParam(JSONObject.toJSONString(params));

        return client.sendSmsWithOptions(sendSmsRequest, runtime);
    }

    /**
     * 指定模板ID群发
     * 签名参数未提供或者为空时，会使用默认签名发送短信
     *
     * @param appid        appid
     * @param appkey       appkey
     * @param nationCode   国家码，如 86 为中国
     * @param phoneNumbers 手机号
     * @param templateId   模板ID
     * @param params       参数
     * @param sign         签名
     * @return SmsMultiSenderResult
     */
    public static SmsMultiSenderResult crSendSms(int appid, String appkey, String nationCode, String[] phoneNumbers, int templateId, String[] params, String sign) {
        SmsMultiSenderResult result = new SmsMultiSenderResult();
        try {
            SmsMultiSender msender = new SmsMultiSender(appid, appkey);
            result = msender.sendWithParam(nationCode, phoneNumbers, templateId, params, sign, "", "");
        } catch (Exception e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 指定模板ID单发短信
     * 签名参数未提供或者为空时，会使用默认签名发送短信
     *
     * @param appid       appid
     * @param appkey      appkey
     * @param nationCode  国家码，如 86 为中国
     * @param phoneNumber 手机号
     * @param templateId  模板ID
     * @param params      参数
     * @param smsSign     签名
     * @return SmsSingleSenderResult
     */
    public static SmsSingleSenderResult crSendSms(int appid, String appkey, String nationCode, String phoneNumber, int templateId, String[] params, String smsSign) {
        SmsSingleSenderResult result = new SmsSingleSenderResult();
        try {
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            result = ssender.sendWithParam(nationCode, phoneNumber, templateId, params, smsSign, "", "");
        } catch (Exception e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 单发短信
     *
     * @param appid       appid
     * @param appkey      appkey
     * @param type        短信类型，0 为普通短信，1 营销短信
     * @param nationCode  国家码，如 86 为中国
     * @param phoneNumber 不带国家码的手机号列表
     * @param msg         短信内容，必须与申请的模板格式一致，否则将返回错误
     * @return SmsSingleSenderResult
     */
    public static SmsSingleSenderResult crSendSms(int appid, String appkey, int type, String nationCode, String phoneNumber, String msg) {
        SmsSingleSenderResult result = new SmsSingleSenderResult();
        try {
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            result = ssender.send(type, nationCode, phoneNumber, msg, "", "");
        } catch (Exception e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 群发短信
     *
     * @param appid        appid
     * @param appkey       appkey
     * @param type         短信类型，0 为普通短信，1 营销短信
     * @param nationCode   国家码，如 86 为中国
     * @param phoneNumbers 不带国家码的手机号列表
     * @param msg          短信内容，必须与申请的模板格式一致，否则将返回错误
     * @return SmsMultiSenderResult
     */
    public static SmsMultiSenderResult crSendSms(int appid, String appkey, int type, String nationCode, String[] phoneNumbers, String msg) {
        SmsMultiSenderResult result = new SmsMultiSenderResult();
        try {
            SmsMultiSender msender = new SmsMultiSender(appid, appkey);
            result = msender.send(type, nationCode, phoneNumbers, msg, "", "");
        } catch (Exception e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return result;
    }
}
