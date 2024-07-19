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
import com.platform.modules.sys.entity.SysConfigEntity;

import java.util.Map;

/**
 * 系统配置信息
 *
 * @author 李鹏军
 */
public interface SysConfigService extends IService<SysConfigEntity> {

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page<SysConfigEntity> queryPage(Map<String, Object> params);

    /**
     * 保存配置信息
     *
     * @param config config
     */
    void add(SysConfigEntity config);

    /**
     * 更新配置信息
     *
     * @param config config
     */
    void update(SysConfigEntity config);

    /**
     * 根据key，更新value
     *
     * @param key   key
     * @param value value
     */
    void updateValueByKey(String key, String value);

    /**
     * 删除配置信息
     *
     * @param ids ids
     */
    void deleteBatch(String[] ids);

    /**
     * 根据key，获取配置的value值
     *
     * @param key key
     * @return String
     */
    String getValue(String key);

    /**
     * 根据key，获取配置的value值
     *
     * @param key          key
     * @param defaultValue 缺省值
     * @return String
     */
    String getValue(String key, String defaultValue);

    /**
     * 根据key，获取value的Object对象
     *
     * @param key   key
     * @param clazz clazz
     * @param <T>   T
     * @return T
     */
    <T> T getConfigObject(String key, Class<T> clazz);

}
