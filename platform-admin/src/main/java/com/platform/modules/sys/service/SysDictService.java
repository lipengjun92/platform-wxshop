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
import com.platform.modules.sys.entity.SysDictEntity;

import java.util.List;
import java.util.Map;

/**
 * 数据字典Service接口
 *
 * @author 李鹏军
 * @since 2019-01-15 11:42:20
 */
public interface SysDictService extends IService<SysDictEntity> {
    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<SysDictEntity> queryAll(Map<String, Object> params);

    /**
     * 查询分页信息
     *
     * @param params 查询参数
     * @return IPage
     */
    Page<SysDictEntity> queryPage(Map<String, Object> params);

    /**
     * 新增实体
     *
     * @param sysDict 实体
     */
    void add(SysDictEntity sysDict);

    /**
     * 根据主键更新实体
     *
     * @param sysDict 实体
     */
    void update(SysDictEntity sysDict);

    /**
     * 根据主键删除
     *
     * @param id 主键
     */
    void delete(String id);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     */
    void deleteBatch(String[] ids);

    /**
     * 根据code查询数据字典
     *
     * @param params 查询参数
     * @return List
     */
    List<SysDictEntity> queryByCode(Map<String, Object> params);
}
