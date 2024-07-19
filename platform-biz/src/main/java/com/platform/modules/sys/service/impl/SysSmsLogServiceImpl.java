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
import com.platform.modules.sys.dao.SysSmsLogDao;
import com.platform.modules.sys.entity.SysSmsLogEntity;
import com.platform.modules.sys.service.SysSmsLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 短信发送日志Service实现类
 *
 * @author 李鹏军
 * @since 2019-02-12 09:51:15
 */
@Service("sysSmsLogService")
public class SysSmsLogServiceImpl extends ServiceImpl<SysSmsLogDao, SysSmsLogEntity> implements SysSmsLogService {
    @Override
    public List<SysSmsLogEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page<SysSmsLogEntity> queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.STIME");
        params.put("asc", false);
        Page<SysSmsLogEntity> page = new Query<SysSmsLogEntity>(params).getPage();
        return page.setRecords(baseMapper.selectSysSmsLogPage(page, params));
    }

    @Override
    public void add(SysSmsLogEntity sysSmsLog) {
        this.save(sysSmsLog);
    }

    @Override
    public void update(SysSmsLogEntity sysSmsLog) {
        this.update(sysSmsLog, new QueryWrapper<>());
    }

    @Override
    public void delete(String id) {
        this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] ids) {
        this.removeByIds(Arrays.asList(ids));
    }
}
