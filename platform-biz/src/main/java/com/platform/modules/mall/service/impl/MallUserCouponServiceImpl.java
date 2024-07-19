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
import com.platform.modules.mall.dao.MallUserCouponDao;
import com.platform.modules.mall.entity.MallUserCouponEntity;
import com.platform.modules.mall.service.MallUserCouponService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 李鹏军
 * @since 2024-07-19 21:02:38
 */
@Service("mallUserCouponService")
public class MallUserCouponServiceImpl extends ServiceImpl<MallUserCouponDao, MallUserCouponEntity> implements MallUserCouponService {

    @Override
    public List<MallUserCouponEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<MallUserCouponEntity> queryAllByWrapper(QueryWrapper<MallUserCouponEntity> queryWrapper) {
        return this.list(queryWrapper);
    }

    @Override
    public Page<MallUserCouponEntity> queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<MallUserCouponEntity> page = new Query<MallUserCouponEntity>(params).getPage();
        return page.setRecords(baseMapper.selectMallUserCouponPage(page, params));
    }

    @Override
    public boolean add(MallUserCouponEntity mallUserCoupon) {
        return this.save(mallUserCoupon);
    }

    @Override
    public boolean update(MallUserCouponEntity mallUserCoupon) {
        return this.updateById(mallUserCoupon);
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
