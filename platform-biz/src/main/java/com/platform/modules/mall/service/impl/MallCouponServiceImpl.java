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
import com.platform.modules.mall.dao.MallCouponDao;
import com.platform.modules.mall.entity.MallCouponEntity;
import com.platform.modules.mall.service.MallCouponService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 李鹏军
 * @since 2026-02-24 10:20:41
 */
@Service("mallCouponService")
public class MallCouponServiceImpl extends ServiceImpl<MallCouponDao, MallCouponEntity> implements MallCouponService {

    @Override
    public List<MallCouponEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<MallCouponEntity> queryAllByWrapper(QueryWrapper<MallCouponEntity> queryWrapper) {
        return this.list(queryWrapper);
    }

    @Override
    public Page<MallCouponEntity> queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<MallCouponEntity> page = new Query<MallCouponEntity>(params).getPage();
        return page.setRecords(baseMapper.selectMallCouponPage(page, params));
    }

    @Override
    public boolean add(MallCouponEntity mallCoupon) {
        return this.save(mallCoupon);
    }

    @Override
    public boolean update(MallCouponEntity mallCoupon) {
        return this.updateById(mallCoupon);
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
