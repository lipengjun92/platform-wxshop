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
package com.platform.config;

import com.platform.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * redis分布式锁
 * 2019-08-07 09:55
 *
 * @author 李鹏军
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RedisLock {

    private final RedisTemplateUtil redisTemplateUtil;

    /**
     * 加锁
     *
     * @param key   key
     * @param value 当前时间+超时时间
     * @return boolean
     */
    public boolean lock(String key, String value) {
        if (redisTemplateUtil.setIfAbsent(key, value)) {
            return true;
        }
        //currentValue=A   这两个线程的value都是B  其中一个线程拿到锁
        String currentValue = redisTemplateUtil.get(key).toString();
        //如果锁过期
        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            //获取上一个锁的时间
            String oldValue = redisTemplateUtil.get(key).toString();

            redisTemplateUtil.set(key, value);
            return !StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue);
        }

        return false;
    }

    /**
     * 解锁
     *
     * @param key   key
     * @param value value
     */
    public void unlock(String key, String value) {
        try {
            String currentValue = redisTemplateUtil.get(key).toString();
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplateUtil.del(key);
            }
        } catch (Exception e) {
            log.error("【redis分布式锁】解锁异常：" + e);
        }
    }

}
