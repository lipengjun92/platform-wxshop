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
import com.platform.modules.sys.entity.SysOrgEntity;

import java.util.List;
import java.util.Map;

/**
 * 组织机构Service接口
 *
 * @author 李鹏军
 * @since 2019-01-21 11:29:22
 */
public interface SysOrgService extends IService<SysOrgEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<SysOrgEntity> queryAll(Map<String, Object> params);

    /**
     * 新增实体
     *
     * @param sysOrg 实体
     */
    void add(SysOrgEntity sysOrg);

    /**
     * 根据主键更新实体
     *
     * @param sysOrg 实体
     */
    void update(SysOrgEntity sysOrg);

    /**
     * 根据主键删除
     *
     * @param orgNo 机构编码
     */
    void delete(String orgNo);

    /**
     * 根据主键批量删除
     *
     * @param orgNos 机构编码集
     */
    void deleteBatch(String[] orgNos);

    /**
     * 根据OrgNo查询子机构
     *
     * @param orgNo 机构编码
     * @return List
     */
    List<SysOrgEntity> queryListByOrgNo(String orgNo);
}
