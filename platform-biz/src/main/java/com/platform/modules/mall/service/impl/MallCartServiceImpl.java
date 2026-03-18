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
import com.platform.modules.mall.dao.MallCartDao;
import com.platform.modules.mall.entity.MallCartEntity;
import com.platform.modules.mall.service.MallCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service实现类
 *
 * @author 李鹏军
 * @since 2024-07-19 20:53:40
 */
@Service("mallCartService")
public class MallCartServiceImpl extends ServiceImpl<MallCartDao, MallCartEntity> implements MallCartService {

    @Override
    public List<MallCartEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<MallCartEntity> queryAllByWrapper(QueryWrapper<MallCartEntity> queryWrapper) {
        return this.list(queryWrapper);
    }

    @Override
    public Page<MallCartEntity> queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<MallCartEntity> page = new Query<MallCartEntity>(params).getPage();
        return page.setRecords(baseMapper.selectMallCartPage(page, params));
    }

    @Override
    public boolean add(MallCartEntity mallCart) {
        return this.save(mallCart);
    }

    @Override
    public boolean update(MallCartEntity mallCart) {
        return this.updateById(mallCart);
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
    public void updateCheck(String[] productIds, Integer isChecked, Long userId) {
        baseMapper.updateCheck(productIds, isChecked, userId);

        // 判断购物车中是否存在此规格商品
        Map<String, Object> cartParam = new HashMap<>();
        cartParam.put("userId", userId);
        List<MallCartEntity> cartInfoList = baseMapper.queryAll(cartParam);
        List<Integer> goodsIds = new ArrayList<>();
        List<MallCartEntity> cartUpdateList = new ArrayList<>();
        for (MallCartEntity cartItem : cartInfoList) {
            if (null != cartItem.getChecked() && 1 == cartItem.getChecked()) {
                goodsIds.add(cartItem.getGoodsId());
            }
        }
        if (!goodsIds.isEmpty()) {
            for (MallCartEntity cartItem : cartInfoList) {
                // 存在原始的
                if (null != cartItem.getChecked() && 1 == cartItem.getChecked()) {
                    for (MallCartEntity cartCrash : cartInfoList) {
                        if (!cartCrash.getId().equals(cartItem.getId())) {
                            cartUpdateList.add(cartCrash);
                            break;
                        }
                    }
                }
            }
        }
        if (!cartUpdateList.isEmpty()) {
            for (MallCartEntity cartItem : cartUpdateList) {
                this.updateById(cartItem);
            }
        }
    }

    @Override
    public void deleteByUserAndProductIds(Long userId, String[] productIdsArray) {
        baseMapper.deleteByUserAndProductIds(userId, productIdsArray);
    }

    @Override
    public List<MallCartEntity> queryList(Map<String, Object> param) {
        return baseMapper.queryList(param);
    }
}
