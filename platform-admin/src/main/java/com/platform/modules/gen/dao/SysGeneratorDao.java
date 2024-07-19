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
package com.platform.modules.gen.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.modules.gen.entity.ColumnEntity;
import com.platform.modules.gen.entity.ResultMapEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @since 2016年12月19日 下午3:32:04
 */
@Mapper
public interface SysGeneratorDao extends BaseMapper<ResultMapEntity> {

    /**
     * queryPage
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<ResultMapEntity> queryPage(Page<ResultMapEntity> page, @Param("params") Map<String, Object> params);

    /**
     * queryTable
     *
     * @param params 查询参数
     * @return ResultMapEntity
     */
    ResultMapEntity queryTable(Map<String, Object> params);

    /**
     * queryColumns
     *
     * @param params 查询参数
     * @return List
     */
    List<ColumnEntity> queryColumns(Map<String, Object> params);
}
