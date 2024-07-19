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
import com.platform.modules.sys.entity.SysOrgEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 组织机构Dao
 *
 * @author 李鹏军
 * @since 2019-01-22 11:11:13
 */
@Mapper
public interface SysOrgDao extends BaseMapper<SysOrgEntity> {

    /**
     * 查询存在的最大ID
     *
     * @param orgNo 机构编码
     * @return String
     */
    String queryMaxIdByParentNo(String orgNo);

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<SysOrgEntity> queryAll(Map<String, Object> params);

    /**
     * 根据orgNo查询所有下级列表
     *
     * @param orgNo 机构编码
     * @return List
     */
    List<SysOrgEntity> selectChildrensByOrgNo(String orgNo);
}
