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
package com.platform.modules.mall.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.mall.entity.MallCommentEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品评论表
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2026-03-15 13:46:21
 */
public interface MallCommentService extends IService<MallCommentEntity> {
    List<MallCommentEntity> queryAll(Map<String, Object> params);

    List<MallCommentEntity> queryAllByWrapper(QueryWrapper<MallCommentEntity> queryWrapper);

    Page<MallCommentEntity> queryPage(Map<String, Object> params);

    boolean add(MallCommentEntity mallComment);

    boolean update(MallCommentEntity mallComment);

    boolean delete(Integer id);

    boolean deleteBatch(Integer[] ids);
}
