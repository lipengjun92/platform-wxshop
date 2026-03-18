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
import com.platform.modules.mall.dao.MallGoodsGalleryDao;
import com.platform.modules.mall.entity.MallGoodsGalleryEntity;
import com.platform.modules.mall.service.MallGoodsGalleryService;
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
@Service("mallGoodsGalleryService")
public class MallGoodsGalleryServiceImpl extends ServiceImpl<MallGoodsGalleryDao, MallGoodsGalleryEntity> implements MallGoodsGalleryService {

    @Override
    public List<MallGoodsGalleryEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<MallGoodsGalleryEntity> queryAllByWrapper(QueryWrapper<MallGoodsGalleryEntity> queryWrapper) {
        return this.list(queryWrapper);
    }

    @Override
    public Page<MallGoodsGalleryEntity> queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<MallGoodsGalleryEntity> page = new Query<MallGoodsGalleryEntity>(params).getPage();
        return page.setRecords(baseMapper.selectMallGoodsGalleryPage(page, params));
    }

    @Override
    public boolean add(MallGoodsGalleryEntity mallGoodsGallery) {
        return this.save(mallGoodsGallery);
    }

    @Override
    public boolean update(MallGoodsGalleryEntity mallGoodsGallery) {
        return this.updateById(mallGoodsGallery);
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
    public List<MallGoodsGalleryEntity> queryList(Map<String, Object> param) {
        return baseMapper.queryList(param);
    }
}
