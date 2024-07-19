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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.modules.wx.TaskExcutor;
import com.platform.modules.wx.entity.TemplateMsgLogEntity;
import com.platform.modules.wx.entity.WxUserEntity;
import com.platform.modules.wx.form.TemplateMsgBatchForm;
import com.platform.modules.wx.service.TemplateMsgLogService;
import com.platform.modules.wx.service.TemplateMsgService;
import com.platform.modules.wx.service.WxUserService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 李鹏军
 */
@Service
@RequiredArgsConstructor
public class TemplateMsgServiceImpl implements TemplateMsgService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TemplateMsgLogService templateMsgLogService;
    private final WxMpService wxService;
    private final WxUserService wxUserService;

    /**
     * 发送微信模版消息,使用固定线程的线程池
     */
    @Override
    @Async
    public void sendTemplateMsg(WxMpTemplateMessage msg) {
        TaskExcutor.submit(() -> {
            String result;
            try {
                result = wxService.getTemplateMsgService().sendTemplateMsg(msg);
            } catch (WxErrorException e) {
                result = e.getMessage();
            }

            //保存发送日志
            TemplateMsgLogEntity log = new TemplateMsgLogEntity(msg, result);
            templateMsgLogService.addLog(log);
        });
    }

    @Override
    @Async
    public void sendMsgBatch(TemplateMsgBatchForm form) {
        logger.info("批量发送模板消息任务开始,参数：{}", form.toString());
        WxMpTemplateMessage.WxMpTemplateMessageBuilder builder = WxMpTemplateMessage.builder()
                .templateId(form.getTemplateId())
                .url(form.getUrl())
                .miniProgram(form.getMiniprogram())
                .data(form.getData());
        Map<String, Object> filterParams = form.getWxUserFilterParams();
        if (filterParams == null) {
            filterParams = new HashMap<>(8);
        }
        long currentPage = 1L, totalPages = Long.MAX_VALUE;
        filterParams.put("limit", "500");
        while (currentPage <= totalPages) {
            filterParams.put("page", String.valueOf(currentPage));
            //按条件查询用户
            Page<WxUserEntity> wxUsers = wxUserService.queryPage(filterParams);
            logger.info("批量发送模板消息任务,使用查询条件，处理第{}页，总用户数：{}", currentPage, wxUsers.getTotal());
            wxUsers.getRecords().forEach(user -> {
                WxMpTemplateMessage msg = builder.toUser(user.getOpenid()).build();
                this.sendTemplateMsg(msg);
            });
            currentPage = wxUsers.getCurrent() + 1L;
            totalPages = wxUsers.getPages();
        }
        logger.info("批量发送模板消息任务结束");
    }
}
