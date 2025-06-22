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
package com.platform.modules.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.mall.dao.MallAdDao;
import com.platform.modules.mall.entity.MallAdEntity;
import com.platform.modules.mall.service.MallAdService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 李鹏军
 * @since 2025-06-20 11:02:14
 */
@Service("mallAdService")
public class MallAdServiceImpl extends ServiceImpl<MallAdDao, MallAdEntity> implements MallAdService {

    @Override
    public List<MallAdEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<MallAdEntity> queryAllByWrapper(QueryWrapper<MallAdEntity> queryWrapper) {
        return this.list(queryWrapper);
    }

    @Override
    public Page<MallAdEntity> queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<MallAdEntity> page = new Query<MallAdEntity>(params).getPage();
        return page.setRecords(baseMapper.selectMallAdPage(page, params));
    }

    @Override
    public boolean add(MallAdEntity mallAd) {
        return this.save(mallAd);
    }

    @Override
    public boolean update(MallAdEntity mallAd) {
        return this.updateById(mallAd);
    }

    @Override
    public boolean delete(Integer id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(Integer[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }
}
