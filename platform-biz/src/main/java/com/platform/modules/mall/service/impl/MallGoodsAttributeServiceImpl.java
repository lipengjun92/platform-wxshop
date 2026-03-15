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
import com.platform.modules.mall.dao.MallGoodsAttributeDao;
import com.platform.modules.mall.entity.MallGoodsAttributeEntity;
import com.platform.modules.mall.service.MallGoodsAttributeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 李鹏军
 * @since 2026-02-24 13:15:28
 */
@Service("mallGoodsAttributeService")
public class MallGoodsAttributeServiceImpl extends ServiceImpl<MallGoodsAttributeDao, MallGoodsAttributeEntity> implements MallGoodsAttributeService {

    @Override
    public List<MallGoodsAttributeEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<MallGoodsAttributeEntity> queryAllByWrapper(QueryWrapper<MallGoodsAttributeEntity> queryWrapper) {
        return this.list(queryWrapper);
    }

    @Override
    public Page<MallGoodsAttributeEntity> queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<MallGoodsAttributeEntity> page = new Query<MallGoodsAttributeEntity>(params).getPage();
        return page.setRecords(baseMapper.selectMallGoodsAttributePage(page, params));
    }

    @Override
    public boolean add(MallGoodsAttributeEntity mallGoodsAttribute) {
        return this.save(mallGoodsAttribute);
    }

    @Override
    public boolean update(MallGoodsAttributeEntity mallGoodsAttribute) {
        return this.updateById(mallGoodsAttribute);
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
