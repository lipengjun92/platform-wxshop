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
import com.platform.modules.wx.entity.WxUserEntity;

import java.util.Map;

/**
 * @author 李鹏军
 */
public interface WxUserService extends IService<WxUserEntity> {
    /**
     * 分页查询用户数据
     *
     * @param params 查询参数
     * @return PageUtils 分页结果
     */
    Page<WxUserEntity> queryPage(Map<String, Object> params);

    /**
     * 根据openid更新用户信息
     *
     * @param openid
     * @return
     */
    WxUserEntity refreshUserInfo(String openid);

    /**
     * 异步批量更新用户信息
     *
     * @param openidList
     */
    void refreshUserInfoAsync(String[] openidList);

    /**
     * 数据存在时更新，否则新增
     *
     * @param user
     */
    void updateOrInsert(WxUserEntity user);

    /**
     * 取消关注，更新关注状态
     *
     * @param openid
     */
    void unsubscribe(String openid);

    /**
     * 同步微信公众号用户列表
     */
    void syncWxUsers();
}
