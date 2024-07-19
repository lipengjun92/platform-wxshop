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

import com.platform.modules.sys.entity.SysCacheEntity;

import java.util.List;
import java.util.Map;

/**
 * redis缓存信息
 *
 * @author 李鹏军
 */
public interface SysCacheService {

    /**
     * 查询缓存
     *
     * @param params 查询参数
     * @return List
     */
    List<SysCacheEntity> queryAll(Map<String, String> params);

    /**
     * 删除缓存
     *
     * @param keys keys
     * @return int
     */
    int deleteBatch(String[] keys);
}
