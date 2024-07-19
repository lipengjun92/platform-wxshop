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
package com.platform.modules.wx.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.wx.entity.MsgTemplateEntity;
import me.chanjar.weixin.common.error.WxErrorException;

import java.util.Map;

/**
 * 消息模板
 *
 * @author 李鹏军
 */
public interface MsgTemplateService extends IService<MsgTemplateEntity> {
    /**
     * 分页查询用户数据
     *
     * @param params 查询参数
     * @return PageUtils 分页结果
     */
    Page<MsgTemplateEntity> queryPage(Map<String, Object> params);

    /**
     * 通过模板名称查询
     *
     * @param name name
     * @return
     */
    MsgTemplateEntity selectByName(String name);

    /**
     * 同步公众号已添加的消息模板
     *
     * @throws WxErrorException
     */
    void syncWxTemplate() throws WxErrorException;
}

