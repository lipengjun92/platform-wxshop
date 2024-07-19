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
import com.platform.modules.sys.entity.SysRoleOrgEntity;

import java.util.List;


/**
 * 角色与机构对应关系
 *
 * @author lipengjun
 * @since 2017年9月18日 上午9:18:38
 */
public interface SysRoleOrgService extends IService<SysRoleOrgEntity> {

    /**
     * 保存或更新
     *
     * @param roleId    角色ID
     * @param orgNoList orgNoList
     */
    void saveOrUpdate(String roleId, List<String> orgNoList);

    /**
     * 根据角色ID，获取机构ID列表
     *
     * @param roleId 角色ID
     * @return List
     */
    List<String> queryOrgNoList(String roleId);

    /**
     * 根据用户ID获取权限机构列表
     *
     * @param userId 用户Id
     * @return String
     */
    String queryOrgNoListByUserId(String userId);

    /**
     * 根据角色ID数组，批量删除
     *
     * @param roleIds 角色ids
     * @return int
     */
    int deleteBatch(String[] roleIds);
}
