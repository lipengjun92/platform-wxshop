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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Constant;
import com.platform.common.utils.Query;
import com.platform.common.utils.TokenGenerator;
import com.platform.modules.sys.dao.SysUserTokenDao;
import com.platform.modules.sys.entity.SysUserTokenEntity;
import com.platform.modules.sys.service.SysUserTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @author 李鹏军
 */
@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {


    @Override
    public String createToken(String userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + Constant.EXPIRE * 1000);

        //判断是否生成过token
        SysUserTokenEntity tokenEntity = this.getById(userId);
        if (tokenEntity == null) {
            tokenEntity = new SysUserTokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //保存token
            this.save(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //更新token
            this.updateById(tokenEntity);
        }

        return token;
    }

    @Override
    public void logout(String userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //修改token
        SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
        tokenEntity.setUserId(userId);
        tokenEntity.setToken(token);
        this.updateById(tokenEntity);
    }

    @Override
    public Page<SysUserTokenEntity> queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.UPDATE_TIME");
        params.put("asc", false);
        Page<SysUserTokenEntity> page = new Query<SysUserTokenEntity>(params).getPage();

        params.put("nowDate", new Date());
        return page.setRecords(baseMapper.selectSysUserTokenPage(page, params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String userId) {
        this.removeById(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void offlineBatch(String[] userIds) {
        this.removeByIds(Arrays.asList(userIds));
    }
}
