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

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.entity.SysUserTokenEntity;

import java.util.Set;

/**
 * shiro相关接口
 *
 * @author 李鹏军
 */
public interface ShiroService extends IService<SysUserTokenEntity> {
    /**
     * 获取用户权限列表
     *
     * @param userId 用户ID
     * @return Set
     */
    Set<String> getUserPermissions(String userId);

    /**
     * 根据token获取用户
     *
     * @param token token
     * @return SysUserEntity
     */
    SysUserTokenEntity queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     *
     * @param userId 用户ID
     * @return SysUserEntity
     */
    SysUserEntity queryUser(String userId);
}
