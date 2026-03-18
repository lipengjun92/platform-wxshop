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
import com.platform.modules.mall.dao.MallGoodsDao;
import com.platform.modules.mall.entity.MallGoodsEntity;
import com.platform.modules.mall.service.MallGoodsService;
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
@Service("mallGoodsService")
public class MallGoodsServiceImpl extends ServiceImpl<MallGoodsDao, MallGoodsEntity> implements MallGoodsService {

    @Override
    public List<MallGoodsEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<MallGoodsEntity> queryAllByWrapper(QueryWrapper<MallGoodsEntity> queryWrapper) {
        return this.list(queryWrapper);
    }

    @Override
    public Page<MallGoodsEntity> queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<MallGoodsEntity> page = new Query<MallGoodsEntity>(params).getPage();
        return page.setRecords(baseMapper.selectMallGoodsPage(page, params));
    }

    @Override
    public boolean add(MallGoodsEntity mallGoods) {
        return this.save(mallGoods);
    }

    @Override
    public boolean update(MallGoodsEntity mallGoods) {
        return this.updateById(mallGoods);
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

    @Override
    public List<MallGoodsEntity> queryCatalogProductList(Map<String, Object> params) {
        return baseMapper.queryCatalogProductList(params);
    }

    @Override
    public List<MallGoodsEntity> queryList(Map<String, Object> param) {
        return baseMapper.queryList(param);
    }

    @Override
    public long queryTotal(Map<String, Object> params) {
        return baseMapper.queryTotal(params);
    }
}
