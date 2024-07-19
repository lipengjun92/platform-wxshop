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

import com.alibaba.fastjson.JSONObject;
import com.platform.config.RedisTemplateUtil;
import com.platform.modules.sys.entity.SysCacheEntity;
import com.platform.modules.sys.service.SysCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 李鹏军
 */
@RequiredArgsConstructor
@Service("sysCacheService")
public class SysCacheServiceImpl implements SysCacheService {
    private final RedisTemplateUtil redisTemplateUtil;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public List<SysCacheEntity> queryAll(Map<String, String> params) {
        SysCacheEntity sysCacheEntity;
        List<SysCacheEntity> result = new ArrayList<>();

        String pattern = params.get("pattern");
        // 获取所有key
        Set<String> keySet = redisTemplateUtil.keys(pattern);
        for (String key : keySet) {
            sysCacheEntity = new SysCacheEntity();
            sysCacheEntity.setCacheKey(key);
            try {
                sysCacheEntity.setValue(JSONObject.toJSON(redisTemplateUtil.get(key)).toString());
            } catch (SerializationException serializationException) {
                // 序列号异常是因为WxJava包里使用的是StringRedisTemplate
                sysCacheEntity.setValue(stringRedisTemplate.opsForValue().get(key));
            } catch (Exception e) {
                sysCacheEntity.setValue("");
            }
            sysCacheEntity.setSeconds(redisTemplateUtil.getExpire(key));
            result.add(sysCacheEntity);
        }
        return result;
    }

    @Override
    public int deleteBatch(String[] keys) {
        for (String key : keys) {
            redisTemplateUtil.del(key);
        }
        return keys.length;
    }
}
