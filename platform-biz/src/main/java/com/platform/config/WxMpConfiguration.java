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

import com.platform.common.utils.Constant;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.redis.RedisTemplateWxRedisOps;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpRedisConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * wechat mp configuration
 *
 * @author 李鹏军
 */
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(WxMpProperties.class)
public class WxMpConfiguration {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private WxMpProperties properties;

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpRedisConfig());
        return wxMpService;
    }

    @Bean
    public WxMpRedisConfigImpl wxMpRedisConfig() {
        WxMpRedisConfigImpl wxMpInRedisConfigStorage = new WxMpRedisConfigImpl(new RedisTemplateWxRedisOps(stringRedisTemplate), Constant.WX_MP_CONFIG);
        wxMpInRedisConfigStorage.setAppId(properties.getAppId());
        wxMpInRedisConfigStorage.setSecret(properties.getSecret());
        wxMpInRedisConfigStorage.setToken(properties.getToken());
        wxMpInRedisConfigStorage.setAesKey(properties.getAesKey());
        return wxMpInRedisConfigStorage;
    }
}
