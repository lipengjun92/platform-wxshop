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
import com.platform.modules.sys.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;

/**
 * 角色
 *
 * @author 李鹏军
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page<SysRoleEntity> queryPage(Map<String, Object> params);

    /**
     * 保存
     *
     * @param role role
     */
    void add(SysRoleEntity role);

    /**
     * 更新
     *
     * @param role role
     */
    void update(SysRoleEntity role);

    /**
     * 删除
     *
     * @param roleIds roleIds
     */
    void deleteBatch(String[] roleIds);

    /**
     * 查询用户权限下的角色ID列表
     *
     * @param params 查询参数
     * @return List
     */
    List<String> queryRoleIdList(Map<String, Object> params);

    /**
     * selectListByMap
     *
     * @param params 查询参数
     * @return List
     */
    List<SysRoleEntity> selectListByMap(Map<String, Object> params);
}
