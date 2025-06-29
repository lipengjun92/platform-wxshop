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
import com.platform.modules.mall.dao.MallTopicDao;
import com.platform.modules.mall.entity.MallTopicEntity;
import com.platform.modules.mall.service.MallTopicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 李鹏军
 * @since 2025-06-22 12:04:32
 */
@Service("mallTopicService")
public class MallTopicServiceImpl extends ServiceImpl<MallTopicDao, MallTopicEntity> implements MallTopicService {

    @Override
    public List<MallTopicEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<MallTopicEntity> queryAllByWrapper(QueryWrapper<MallTopicEntity> queryWrapper) {
        return this.list(queryWrapper);
    }

    @Override
    public Page<MallTopicEntity> queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<MallTopicEntity> page = new Query<MallTopicEntity>(params).getPage();
        return page.setRecords(baseMapper.selectMallTopicPage(page, params));
    }

    @Override
    public boolean add(MallTopicEntity mallTopic) {
        return this.save(mallTopic);
    }

    @Override
    public boolean update(MallTopicEntity mallTopic) {
        return this.updateById(mallTopic);
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
