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
import com.platform.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author 李鹏军
 */
public interface SysUserService extends IService<SysUserEntity> {

    /**
     * 查询所有
     *
     * @param params 查询参数
     * @return List
     */
    List<SysUserEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page<SysUserEntity> queryPage(Map<String, Object> params);

    /**
     * 查询用户的所有菜单ID
     *
     * @param userId 用户Id
     * @return List
     */
    List<String> queryAllMenuId(String userId);

    /**
     * 根据用户名，查询系统用户
     *
     * @param userName 用户名
     * @return SysUserEntity
     */
    SysUserEntity queryByUserName(String userName);

    /**
     * 保存用户
     *
     * @param user   用户
     * @param params 查询参数
     */
    void add(SysUserEntity user, Map<String, Object> params);

    /**
     * 修改用户
     *
     * @param user   用户
     * @param params 查询参数
     */
    void update(SysUserEntity user, Map<String, Object> params);

    /**
     * 删除用户
     *
     * @param userIds 用户Ids
     */
    void deleteBatch(String[] userIds);

    /**
     * 修改密码
     *
     * @param userId      用户Id
     * @param password    原密码
     * @param newPassword 新密码
     * @return boolean
     */
    boolean updatePassword(String userId, String password, String newPassword);

    /**
     * 重置密码
     *
     * @param userIds 用户Ids
     */
    void resetPw(String[] userIds);
}
