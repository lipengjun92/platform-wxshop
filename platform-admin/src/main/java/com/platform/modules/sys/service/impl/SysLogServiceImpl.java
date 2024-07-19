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
import com.platform.common.utils.Query;
import com.platform.modules.sys.dao.SysLogDao;
import com.platform.modules.sys.entity.SysLogEntity;
import com.platform.modules.sys.service.SysLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 李鹏军
 */
@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Override
    public Page<SysLogEntity> queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        String ip = (String) params.get("ip");

        Page<SysLogEntity> page = new Query<SysLogEntity>(params).getPage();

        return baseMapper.selectPage(page,
                new QueryWrapper<SysLogEntity>()
                        .like(StringUtils.isNotBlank(ip), "IP", ip)
                        .and(StringUtils.isNotBlank(key), queryWrapper -> queryWrapper.like("USER_NAME", key).or().like("OPERATION", key))
                        .orderByDesc("CREATE_TIME"));
    }
}
