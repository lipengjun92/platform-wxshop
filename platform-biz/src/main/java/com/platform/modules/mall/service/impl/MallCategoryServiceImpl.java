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
import com.platform.modules.mall.dao.MallCategoryDao;
import com.platform.modules.mall.entity.MallCategoryEntity;
import com.platform.modules.mall.service.MallCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 李鹏军
 * @since 2024-07-19 21:57:50
 */
@Service("mallCategoryService")
public class MallCategoryServiceImpl extends ServiceImpl<MallCategoryDao, MallCategoryEntity> implements MallCategoryService {

    @Override
    public List<MallCategoryEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<MallCategoryEntity> queryAllByWrapper(QueryWrapper<MallCategoryEntity> queryWrapper) {
        return this.list(queryWrapper);
    }

    @Override
    public Page<MallCategoryEntity> queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<MallCategoryEntity> page = new Query<MallCategoryEntity>(params).getPage();
        return page.setRecords(baseMapper.selectMallCategoryPage(page, params));
    }

    @Override
    public boolean add(MallCategoryEntity mallCategory) {
        return this.save(mallCategory);
    }

    @Override
    public boolean update(MallCategoryEntity mallCategory) {
        return this.updateById(mallCategory);
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
