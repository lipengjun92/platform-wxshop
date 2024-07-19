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
package com.platform.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.wx.dao.WxMsgDao;
import com.platform.modules.wx.entity.WxMsgEntity;
import com.platform.modules.wx.service.WxMsgService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Map;

/**
 * @author 李鹏军
 * @date 2021-05-06 18:30:15
 */
@Service("wxMsgService")
public class WxMsgServiceImpl extends ServiceImpl<WxMsgDao, WxMsgEntity> implements WxMsgService {

    @Override
    public Page<WxMsgEntity> queryPage(Map<String, Object> params) {
        String msgTypes = (String) params.get("msgTypes");
        String startTime = (String) params.get("startTime");
        String openid = (String) params.get("openid");

        Page<WxMsgEntity> page = new Query<WxMsgEntity>(params).getPage();

        return baseMapper.selectPage(page,
                new QueryWrapper<WxMsgEntity>()
                        .in(StringUtils.hasText(msgTypes), "MSG_TYPE", Arrays.asList(msgTypes.split(",")))
                        .eq(StringUtils.hasText(openid), "OPENID", openid)
                        .ge(StringUtils.hasText(startTime), "CREATE_TIME", startTime)
                        .orderByDesc("CREATE_TIME"));
    }

    /**
     * 记录msg，异步入库
     *
     * @param msg
     */
    @Override
    @Async
    public void addWxMsg(WxMsgEntity msg) {
        baseMapper.insert(msg);
    }
}
