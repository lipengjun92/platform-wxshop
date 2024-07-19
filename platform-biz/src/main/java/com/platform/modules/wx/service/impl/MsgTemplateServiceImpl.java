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
import com.platform.modules.wx.dao.MsgTemplateDao;
import com.platform.modules.wx.entity.MsgTemplateEntity;
import com.platform.modules.wx.service.MsgTemplateService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 李鹏军
 */
@RequiredArgsConstructor
@Service("msgTemplateService")
public class MsgTemplateServiceImpl extends ServiceImpl<MsgTemplateDao, MsgTemplateEntity> implements MsgTemplateService {
    private final WxMpService wxService;

    @Override
    public Page<MsgTemplateEntity> queryPage(Map<String, Object> params) {
        String title = (String) params.get("title");
        String name = (String) params.get("name");

        Page<MsgTemplateEntity> page = new Query<MsgTemplateEntity>(params).getPage();
        return baseMapper.selectPage(page,
                new QueryWrapper<MsgTemplateEntity>()
                        .like(StringUtils.hasText(title), "TITLE", title)
                        .like(StringUtils.hasText(name), "NAME", name)
        );
    }

    @Override
    public MsgTemplateEntity selectByName(String name) {
        Assert.hasText(name, "模板名称不得为空");
        return this.getOne(new QueryWrapper<MsgTemplateEntity>()
                .eq("NAME", name)
                .eq("STATUS", 1)
                .last("LIMIT 1"));
    }

    @Override
    public void syncWxTemplate() throws WxErrorException {
        baseMapper.delete(null);
        List<WxMpTemplate> wxMpTemplateList = wxService.getTemplateMsgService().getAllPrivateTemplate();
        List<MsgTemplateEntity> templates = wxMpTemplateList.stream().map(MsgTemplateEntity::new).collect(Collectors.toList());
        this.saveBatch(templates);
    }
}
