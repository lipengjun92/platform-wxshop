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
import com.platform.common.utils.Query;
import com.platform.modules.sys.dao.SysDictGroupDao;
import com.platform.modules.sys.entity.SysDictGroupEntity;
import com.platform.modules.sys.service.SysDictGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 数据字典分组Service实现类
 *
 * @author 李鹏军
 * @since 2019-01-15 11:42:20
 */
@Service("sysDictGroupService")
public class SysDictGroupServiceImpl extends ServiceImpl<SysDictGroupDao, SysDictGroupEntity> implements SysDictGroupService {

    @Override
    public List<SysDictGroupEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page<SysDictGroupEntity> queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.CREATE_TIME");
        params.put("asc", false);
        Page<SysDictGroupEntity> page = new Query<SysDictGroupEntity>(params).getPage();
        return page.setRecords(baseMapper.selectSysDictGroupPage(page, params));
    }

    @Override
    public void add(SysDictGroupEntity sysDictGroup) {
        this.save(sysDictGroup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDictGroupEntity sysDictGroup) {
        this.updateById(sysDictGroup);
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
