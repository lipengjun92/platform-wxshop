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
import com.platform.modules.mall.dao.MallOrderDao;
import com.platform.modules.mall.entity.MallOrderEntity;
import com.platform.modules.mall.service.MallOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 订单服务实现。
 * <p>
 * 基于 MyBatis-Plus 的 ServiceImpl 提供订单模块的 CRUD 与分页查询实现。
 * </p>
 *
 * @author 李鹏军
 * @since 2026-03-15 14:30:00
 */
@Service("mallOrderService")
public class MallOrderServiceImpl extends ServiceImpl<MallOrderDao, MallOrderEntity> implements MallOrderService {

    /**
     * 查询订单列表。
     */
    @Override
    public List<MallOrderEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    /**
     * 按 QueryWrapper 查询订单列表。
     */
    @Override
    public List<MallOrderEntity> queryAllByWrapper(QueryWrapper<MallOrderEntity> queryWrapper) {
        return this.list(queryWrapper);
    }

    /**
     * 分页查询订单，并按 ID 倒序。
     */
    @Override
    public Page<MallOrderEntity> queryPage(Map<String, Object> params) {
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<MallOrderEntity> page = new Query<MallOrderEntity>(params).getPage();
        return page.setRecords(baseMapper.selectMallOrderPage(page, params));
    }

    /**
     * 新增订单。
     */
    @Override
    public boolean add(MallOrderEntity mallOrder) {
        return this.save(mallOrder);
    }

    /**
     * 修改订单。
     */
    @Override
    public boolean update(MallOrderEntity mallOrder) {
        return this.updateById(mallOrder);
    }

    /**
     * 删除订单。
     */
    @Override
    public boolean delete(Integer id) {
        return this.removeById(id);
    }

    /**
     * 批量删除订单。
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(Integer[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }
}
