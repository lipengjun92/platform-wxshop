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
import com.platform.modules.sys.entity.SysMenuEntity;

import java.util.List;

/**
 * 菜单管理
 *
 * @author 李鹏军
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId   父级菜单ID
     * @param menuIdList menuIdList
     * @return List
     */
    List<SysMenuEntity> queryListParentId(String parentId, List<String> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId 父级菜单ID
     * @return List
     */
    List<SysMenuEntity> queryListParentId(String parentId);

    /**
     * 获取不包含按钮的菜单列表
     *
     * @return List
     */
    List<SysMenuEntity> queryNotButtonList();

    /**
     * 获取用户菜单列表
     *
     * @param userId 用户Id
     * @return List
     */
    List<SysMenuEntity> getUserMenuList(String userId);

    /**
     * 删除
     *
     * @param menuId 菜单ID
     * @return 删除结果
     */
    boolean delete(String menuId);

    /**
     * 查询所有菜单
     *
     * @return List
     */
    List<SysMenuEntity> queryList();

    /**
     * 新增菜单
     *
     * @param menu menu
     * @return 新增结果
     */
    boolean add(SysMenuEntity menu);
}
