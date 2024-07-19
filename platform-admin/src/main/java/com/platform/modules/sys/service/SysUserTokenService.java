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
package com.platform.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.sys.entity.SysUserTokenEntity;

import java.util.Map;

/**
 * 用户Token
 *
 * @author 李鹏军
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {

    /**
     * 生成token
     *
     * @param userId 用户Id
     * @return Map
     */
    String createToken(String userId);

    /**
     * 退出，修改token值
     *
     * @param userId 用户Id
     */
    void logout(String userId);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page<SysUserTokenEntity> queryPage(Map<String, Object> params);

    /**
     * 根据主键删除
     *
     * @param userId 用户Id
     */
    void delete(String userId);

    /**
     * 批量下线用户(删除用户token记录)
     *
     * @param userIds 用户Ids
     */
    void offlineBatch(String[] userIds);
}
