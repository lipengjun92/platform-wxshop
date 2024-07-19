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
package com.platform.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Constant;
import com.platform.common.utils.Query;
import com.platform.config.RedisTemplateUtil;
import com.platform.modules.sys.dao.SysConfigDao;
import com.platform.modules.sys.entity.SysConfigEntity;
import com.platform.modules.sys.service.SysConfigService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;

/**
 * @author 李鹏军
 */
@Service("sysConfigService")
@RequiredArgsConstructor
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {

    private final RedisTemplateUtil redisTemplateUtils;

    @Override
    public Page<SysConfigEntity> queryPage(Map<String, Object> params) {
        String paramKey = (String) params.get("paramKey");
        Page<SysConfigEntity> page = new Query<SysConfigEntity>(params).getPage();
        return baseMapper.selectPage(page,
                new QueryWrapper<SysConfigEntity>()
                        .like(StringUtils.isNotBlank(paramKey), "PARAM_KEY", paramKey)
                        .eq("STATUS", 1));
    }

    @Override
    public void add(SysConfigEntity config) {
        this.save(config);
        saveOrUpdateFromRedis(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysConfigEntity config) {
        baseMapper.updateById(config);
        saveOrUpdateFromRedis(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateValueByKey(String key, String value) {
        baseMapper.updateValueByKey(key, value);
        deleteFromRedis(key);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] ids) {
        for (String id : ids) {
            SysConfigEntity config = this.getById(id);
            deleteFromRedis(config.getParamKey());
        }

        this.removeByIds(Arrays.asList(ids));
    }

    @Override
    public String getValue(String key) {
        SysConfigEntity config = getFromRedis(key);
        if (config == null) {
            config = baseMapper.queryByKey(key);
            saveOrUpdateFromRedis(config);
        }

        return config == null ? null : config.getParamValue();
    }

    @Override
    public <T> T getConfigObject(String key, Class<T> clazz) {
        String value = getValue(key);
        if (StringUtils.isNotBlank(value)) {
            return new Gson().fromJson(value, clazz);
        }

        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new BusinessException("获取参数失败");
        }
    }

    private void saveOrUpdateFromRedis(SysConfigEntity config) {
        if (config == null) {
            return;
        }
        String key = Constant.SYS_CACHE + config.getParamKey();
        redisTemplateUtils.set(key, config);
    }

    @Override
    public String getValue(String key, String defaultValue) {
        String value = getValue(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return value;
    }

    private void deleteFromRedis(String configKey) {
        String key = Constant.SYS_CACHE + configKey;
        redisTemplateUtils.del(key);
    }

    private SysConfigEntity getFromRedis(String configKey) {
        String key = Constant.SYS_CACHE + configKey;
        return (SysConfigEntity) redisTemplateUtils.get(key);
    }
}
