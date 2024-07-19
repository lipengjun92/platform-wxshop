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
import com.platform.modules.wx.dao.TemplateMsgLogDao;
import com.platform.modules.wx.entity.TemplateMsgLogEntity;
import com.platform.modules.wx.service.TemplateMsgLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 李鹏军
 */
@Service
public class TemplateMsgLogServiceImpl extends ServiceImpl<TemplateMsgLogDao, TemplateMsgLogEntity> implements TemplateMsgLogService {

    @Override
    public Page<TemplateMsgLogEntity> queryPage(Map<String, Object> params) {
        Page<TemplateMsgLogEntity> page = new Query<TemplateMsgLogEntity>(params).getPage();

        return baseMapper.selectPage(page, new QueryWrapper<>());
    }

    /**
     * 记录log，异步入库
     *
     * @param log
     */
    @Override
    @Async
    public void addLog(TemplateMsgLogEntity log) {
        this.baseMapper.insert(log);
    }
}
