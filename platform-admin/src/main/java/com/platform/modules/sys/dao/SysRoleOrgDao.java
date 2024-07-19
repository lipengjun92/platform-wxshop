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
package com.platform.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.modules.sys.entity.SysRoleOrgEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色与机构对应关系Dao
 *
 * @author 李鹏军
 * @since 2019-01-21 17:20:07
 */
@Mapper
public interface SysRoleOrgDao extends BaseMapper<SysRoleOrgEntity> {

    /**
     * 删除根据角色Id
     *
     * @param roleId 角色Id
     * @return int
     */
    int deleteByRoleId(String roleId);

    /**
     * 根据角色ID，获取机构ID列表
     *
     * @param roleId 角色Id
     * @return List
     */
    List<String> queryOrgNoList(String roleId);

    /**
     * 根据用户ID获取权限机构列表
     *
     * @param userId 用户ID
     * @return List
     */
    List<String> queryOrgNoListByUserId(String userId);

    /**
     * offlineBatch
     *
     * @param roleIds 角色Ids
     * @return int
     */
    int deleteBatch(String[] roleIds);
}
